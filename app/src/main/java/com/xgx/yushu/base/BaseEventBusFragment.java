package com.xgx.yushu.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lzy.okgo.OkGo;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.EventCenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.bingoogolapple.titlebar.BGATitleBar;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseEventBusFragment extends SupportFragment implements TakePhoto.TakeResultListener, InvokeListener {
    private String TAG = "BaseEventBusFragment";

    protected abstract void onEventComming(EventCenter eventCenter);

    public abstract boolean isBindEventBusHere();

    private InvokeParam invokeParam;
    private TakePhoto takePhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        if (this.isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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

    protected void initToolbarNav(BGATitleBar toolbar) {
        toolbar.setLeftDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                pop();
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {
                onTitleClickRightCtv();
            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });
    }

    protected void onTitleClickRightCtv() {

    }

    protected View mLayoutView;

    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();

    public abstract void initPresenter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutView = getCreateView(inflater, container);
        ButterKnife.bind(this, mLayoutView);
        initView();
        return mLayoutView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initPresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    /**
     * 获取Fragment布局文件的View
     *
     * @param inflater
     * @param container
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }


    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
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
}
