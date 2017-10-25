package com.xgx.yushu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.presenter.JsonCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.ui.LoginActivity;
import com.xgx.yushu.utils.Constact;
import com.xgx.yushu.utils.CustomHelper;
import com.xgx.yushu.utils.GlideCircleTransform;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.utils.UnicodeUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class SettingFragment extends BaseEventBusFragment {
    @Bind(R.id.avatarTv)
    SuperTextView avatarTv;
    @Bind(R.id.nickNameTv)
    SuperTextView nickNameTv;
    @Bind(R.id.realNameTv)
    SuperTextView realNameTv;
    @Bind(R.id.genderTv)
    SuperTextView genderTv;
    @Bind(R.id.phoneTv)
    SuperTextView phoneTv;
    @Bind(R.id.birthdayTv)
    SuperTextView birthdayTv;
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.privacyTv)
    SuperTextView privacyTv;
    @Bind(R.id.exitBtn)
    SuperButton exitBtn;
    private boolean isInitCache = false;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (EventCenter.REFRESH_Setting_FRAGMENT == eventCenter.getEventCode()) {
            getDatas();
        }
    }

    @Override
    public boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView() {
        initToolbarNav(titlebar);
    }

    @Override
    public void initPresenter() {
        initInfo();
        getDatas();
    }

    private void initInfo() {
        if (LoginInfomation.getInstance().isLogin()) {
            User user = LoginInfomation.getInstance().getUser();
            Glide.with(getActivity()).load(MyUrl.baseIp + LoginInfomation.getInstance().getUser().getUser_headpic()).error(R.mipmap.head_me).transform(new GlideCircleTransform(getActivity())).into(avatarTv.getRightIconIV());
            nickNameTv.setRightString(user.getUser_nickname());
            realNameTv.setRightString(user.getUser_name());
            genderTv.setRightString("1".equals(user.getUser_sex()) ? "男" : "女");
            phoneTv.setRightString(UnicodeUtils.changePhone(user.getUser_tel()));
            birthdayTv.setRightString(TimeUtils.millis2String(user.getUser_birthday(), new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())));
        }
    }

    private void getDatas() {
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.getBaseInfo)
                .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(MyUrl.getBaseInfo + LoginInfomation.getInstance().getUser().getUser_id())
                .execute(new JsonCallback<MyResponse>() {
                    @Override
                    public void onSuccess(Response<MyResponse> response) {
                        ResultData userData = response.body().data;
                        User user = ((JSONObject) userData.info).toJavaObject(User.class);
                        LoginInfomation.getInstance().setUser(user);
                        SPUtils.getInstance().put(Constact.loginUserSp, JSON.toJSONString(user));
                        initInfo();
                    }

                    @Override
                    public void onError(Response<MyResponse> response) {
                        super.onError(response);
                        ToastUtils.showShort(response.getException().getMessage());
                    }

                    @Override
                    public void onCacheSuccess(Response<MyResponse> response) {
                        super.onCacheSuccess(response);
                        if (!isInitCache) {
                            onSuccess(response);
                            isInitCache = true;
                        }
                    }
                });
    }


    @OnClick({R.id.avatarTv, R.id.nickNameTv, R.id.realNameTv, R.id.genderTv, R.id.phoneTv, R.id.birthdayTv, R.id.privacyTv, R.id.exitBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatarTv:
                //切换头像
                StyledDialog.buildIosSingleChoose(Arrays.asList(new String[]{"拍照", "从相册里选择"}), new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        switch (i) {
                            case 0:
                                CustomHelper.of().onClick(0, getTakePhoto());

                                break;
                            case 1:
                                CustomHelper.of().onClick(1, getTakePhoto());

                                break;
                        }
                    }
                }).setCancelable(true, true)
                        .show();
                break;
            case R.id.nickNameTv:
                //修改昵称
                start(SettingEditFragment.newInstance(LoginInfomation.getInstance().getUser().getUser_nickname(), "user_nickname"));
                break;
            case R.id.realNameTv:
                start(SettingEditFragment.newInstance(LoginInfomation.getInstance().getUser().getUser_name(), "user_name"));

                break;
            case R.id.genderTv:
                StyledDialog.buildIosSingleChoose(Arrays.asList(new String[]{"男", "女"}), new MyItemDialogListener() {
                    @Override
                    public void onItemClick(final CharSequence charSequence, int i) {
                        genderTv.setRightString(charSequence);
                        OkGo.<MyResponse>post(MyUrl.baseUrl)
                                .params("service", MyUrl.updateUserInfo)
                                .params("user_sex", charSequence.toString())
                                .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                                .execute(new DialogCallback<MyResponse>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<MyResponse> response) {
                                        ToastUtils.showShort("更改成功");
                                        getDatas();
                                    }

                                    @Override
                                    public void onError(Response<MyResponse> response) {
                                        super.onError(response);
                                        ToastUtils.showShort(response.getException().getMessage());
                                    }
                                });

                    }
                }).setCancelable(true, true).show();
                break;
            case R.id.phoneTv:
                break;
            case R.id.birthdayTv:
                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(final Date date, View v) {//选中事件回调
                        birthdayTv.setRightString(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())));
                        OkGo.<MyResponse>post(MyUrl.baseUrl)
                                .params("service", MyUrl.updateUserInfo)
                                .params("user_birthday", TimeUtils.date2Millis(date))
                                .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                                .execute(new DialogCallback<MyResponse>(getActivity()) {
                                    @Override
                                    public void onSuccess(Response<MyResponse> response) {
                                        ToastUtils.showShort("更改成功");
                                        getDatas();
                                    }

                                    @Override
                                    public void onError(Response<MyResponse> response) {
                                        super.onError(response);
                                        ToastUtils.showShort(response.getException().getMessage());
                                    }
                                });

                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .build();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(TimeUtils.millis2Date(LoginInfomation.getInstance().getUser().getUser_birthday()));
                pvTime.setDate(calendar);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
                break;
            case R.id.privacyTv:
                start(PrivacyFragment.newInstance());
                break;
            case R.id.exitBtn:
                SPUtils.getInstance().remove(Constact.loginUserSp);
                LoginInfomation.getInstance().setUser(null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
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
        if (result.getImage() != null) {
            OkGo.<MyResponse>post(MyUrl.baseUrl)
                    .params("service", MyUrl.UploadPic)
                    .params("file", new File(result.getImage().getCompressPath()))
                    .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                    .execute(new DialogCallback<MyResponse>(getActivity()) {
                        @Override
                        public void onSuccess(Response<MyResponse> response) {
                            ToastUtils.showShort("更改成功");
                            String url = response.body().data.url;
                            OkGo.<MyResponse>post(MyUrl.baseUrl)
                                    .params("service", MyUrl.updateUserInfo)
                                    .params("user_headpic", url)
                                    .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                                    .execute(new DialogCallback<MyResponse>(getActivity()) {
                                        @Override
                                        public void onSuccess(Response<MyResponse> response) {
                                            ToastUtils.showShort("更改成功");
                                            getDatas();
                                        }

                                        @Override
                                        public void onError(Response<MyResponse> response) {
                                            super.onError(response);
                                            ToastUtils.showShort(response.getException().getMessage());
                                        }
                                    });
                        }

                        @Override
                        public void onError(Response<MyResponse> response) {
                            super.onError(response);
                            ToastUtils.showShort(response.getException().getMessage());
                        }
                    });

        }
    }


}
