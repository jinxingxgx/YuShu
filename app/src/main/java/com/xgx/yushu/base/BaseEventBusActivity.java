package com.xgx.yushu.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.EventCenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public abstract class BaseEventBusActivity extends SupportActivity implements TakePhoto.TakeResultListener, InvokeListener {
    private TakePhoto takePhoto;
    private String TAG = "BaseEventBusActivity";
    private InvokeParam invokeParam;

    protected abstract void onEventComming(EventCenter eventCenter);

    public abstract boolean isBindEventBusHere();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        initContentView();
        ButterKnife.bind(this);
        // 隐藏标题栏
        initView();
        initPresenter();
        if (this.isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        resetToast();
        // setStatusBar();
        //在Application的oncreate方法里:
        //传入context

    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    protected abstract void initPresenter();


    protected abstract void initView();

    protected abstract void initContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetToast();
        if (this.isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Subscribe
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            this.onEventComming(eventCenter);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //拍照takephoto
    @Override
    public void takeSuccess(TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }


    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    private void resetToast() {

        ToastUtils.setMsgColor(0xFEFFFFFF);
        ToastUtils.setBgColor(0xFEFFFFFF);
        ToastUtils.setBgResource(-1);
        ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.offset_64));
    }
}
