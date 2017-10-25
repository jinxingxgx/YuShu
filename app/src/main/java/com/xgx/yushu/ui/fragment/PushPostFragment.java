package com.xgx.yushu.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ToastUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.PhotoAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.CustomHelper;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.widget.MultiImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class PushPostFragment extends BaseEventBusFragment {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.title)
    EditText title;
    @Bind(R.id.contentTv)
    EditText contentTv;
    @Bind(R.id.takePhotosTv)
    TextView takePhotosTv;
    @Bind(R.id.ngl_images)
    MultiImageView nglImages;
    @Bind(R.id.pushBtn)
    SuperButton pushBtn;
    private ArrayList<String> photos;
    private PhotoAdapter photosAdapter;

    public static PushPostFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        PushPostFragment fragment = new PushPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_push_post;
    }

    @Override
    public void initView() {

        photos = new ArrayList<>();
        titlebar.setTitleText(getArguments().getInt("type") == 1 ? "最新动态" : "公共建议");
        initToolbarNav(titlebar);
    }

    @Override
    public void initPresenter() {
        //请求参数
        getData();
    }

    private void getData() {

    }


    @OnClick({R.id.takePhotosTv, R.id.pushBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhotosTv:
                StyledDialog.buildIosSingleChoose(Arrays.asList(new String[]{"拍照", "从相册里选择"}), new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        switch (i) {
                            case 0:
                                CustomHelper.of().onClick(0, getTakePhoto());

                                break;
                            case 1:
                                CustomHelper.of().onClick(2, getTakePhoto());

                                break;
                        }
                    }
                }).setCancelable(true, true)
                        .show();

                break;
            case R.id.pushBtn:
                PostRequest<MyResponse> request = OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.doPost)
                        .params("post_title", title.getText().toString())
                        .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                        .params("post_type", getArguments().getInt("type"))
                        .params("post_desc", contentTv.getText().toString());
                for (int i = 0; i < photos.size(); i++) {
                    request.params("filelist[]", new File(photos.get(i)));
                }
                request.tag(this).execute(new DialogCallback<MyResponse>(getActivity()) {

                    @Override
                    public void onSuccess(Response<MyResponse> response) {
                        ToastUtils.showShort("上传成功");
                        EventBus.getDefault().post(new EventCenter(EventCenter.REFRESH_PUSH_FRAGMENT));
                        pop();
                    }

                    @Override
                    public void onError(Response<MyResponse> response) {
                        super.onError(response);
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });
                break;
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (result.getImages() != null && result.getImages().size() > 0) {
            for (int i = 0; i < result.getImages().size(); i++) {
                photos.add(result.getImages().get(i).getCompressPath());
            }
            nglImages.setList(photos);
        }
    }

}
