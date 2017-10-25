package com.xgx.yushu.ui.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.HomeMenuAdapter;
import com.xgx.yushu.adapter.PhotoAdapter;
import com.xgx.yushu.adapter.TransferListAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.HomeMenuEntity;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.TransferInfo;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.CustomHelper;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.widget.FullyGridLayoutManager;
import com.xgx.yushu.widget.MultiImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class PushTransferFragment extends BaseEventBusFragment {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.title)
    EditText title;
    @Bind(R.id.contentTv)
    EditText contentTv;
    @Bind(R.id.typeStv)
    SuperTextView typeStv;
    @Bind(R.id.takePhotosTv)
    TextView takePhotosTv;
    @Bind(R.id.ngl_images)
    MultiImageView nglImages;
    @Bind(R.id.pushBtn)
    SuperButton pushBtn;
    private ArrayList<String> photos;
    private PhotoAdapter photosAdapter;

    public static PushTransferFragment newInstance() {

        Bundle args = new Bundle();

        PushTransferFragment fragment = new PushTransferFragment();
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
        return R.layout.fragment_push_transfer;
    }

    @Override
    public void initView() {
        photos = new ArrayList<>();
        titlebar.setTitleText("发布宝贝");
        initToolbarNav(titlebar);
    }

    @Override
    public void initPresenter() {
        //请求参数
        getData();
    }

    private void getData() {

    }


    @OnClick({R.id.typeStv, R.id.takePhotosTv, R.id.pushBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.typeStv:
                StyledDialog.buildIosSingleChoose(Arrays.asList(new String[]{"置换", "馈赠"}), new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        typeStv.setRightString(charSequence);
                    }
                }).setCancelable(true, true)
                        .show();
                break;
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
                if (photos.size() == 0) {
                    ToastUtils.showShort("请选择图片");
                    return;
                }
                PostRequest<MyResponse> request = OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.doTransfer)
                        .params("transfer_title", title.getText().toString())
                        .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                        .params("transfer_desc", contentTv.getText().toString())
                        .params("transfer_type", typeStv.getRightString().equals("置换") ? "2" : "1");
                for (int i = 0; i < photos.size(); i++) {
                    request.params("filelist[]", new File(photos.get(i)));
                }
                request.tag(this).execute(new DialogCallback<MyResponse>(getActivity()) {

                    @Override
                    public void onSuccess(Response<MyResponse> response) {
                        ToastUtils.showShort("上传成功");
                        EventBus.getDefault().post(new EventCenter(EventCenter.REFRESH_TRANSFER_FRAGMENT));
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
