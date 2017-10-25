package com.xgx.yushu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.alibaba.fastjson.JSON;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.Constact;
import com.xgx.yushu.utils.MyUrl;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class PrivacyFragment extends BaseEventBusFragment {

    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.settingIv1)
    SuperTextView settingIv1;
    @Bind(R.id.settingIv2)
    SuperTextView settingIv2;
    @Bind(R.id.settingIv3)
    SuperTextView settingIv3;

    public static PrivacyFragment newInstance() {

        Bundle args = new Bundle();
        PrivacyFragment fragment = new PrivacyFragment();
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
        return R.layout.fragment_privacy;
    }

    @Override
    public void initView() {
        initToolbarNav(titlebar);
    }

    @Override
    public void initPresenter() {
        if (LoginInfomation.getInstance().isLogin()) {
            User user = LoginInfomation.getInstance().getUser();
            settingIv1.setSwitchIsChecked(1 == user.getUser_name_set() ? true : false);
            settingIv2.setSwitchIsChecked(1 == user.getUser_tel_set() ? true : false);
            settingIv3.setSwitchIsChecked(1 == user.getUser_birthday_set() ? true : false);
        }

        settingIv1.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.updateUserInfo)
                        .params("user_name_set", b ? 1 : 0)
                        .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                        .execute(new DialogCallback<MyResponse>(getActivity()) {
                            @Override
                            public void onSuccess(Response<MyResponse> response) {
                                ToastUtils.showShort("更改成功");
                                EventBus.getDefault().post(new EventCenter(EventCenter.REFRESH_Setting_FRAGMENT));
                            }

                            @Override
                            public void onError(Response<MyResponse> response) {
                                super.onError(response);
                                ToastUtils.showShort(response.getException().getMessage());
                            }
                        });
            }
        });
        settingIv2.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.updateUserInfo)
                        .params("user_tel_set", b ? 1 : 0)
                        .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                        .execute(new DialogCallback<MyResponse>(getActivity()) {
                            @Override
                            public void onSuccess(Response<MyResponse> response) {
                                ToastUtils.showShort("更改成功");
                                EventBus.getDefault().post(new EventCenter(EventCenter.REFRESH_Setting_FRAGMENT));
                            }

                            @Override
                            public void onError(Response<MyResponse> response) {
                                super.onError(response);
                                ToastUtils.showShort(response.getException().getMessage());
                            }
                        });
            }
        });
        settingIv3.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.updateUserInfo)
                        .params("user_birthday_set", b ? 1 : 0)
                        .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                        .execute(new DialogCallback<MyResponse>(getActivity()) {
                            @Override
                            public void onSuccess(Response<MyResponse> response) {
                                ToastUtils.showShort("更改成功");
                                EventBus.getDefault().post(new EventCenter(EventCenter.REFRESH_Setting_FRAGMENT));
                            }

                            @Override
                            public void onError(Response<MyResponse> response) {
                                super.onError(response);
                                ToastUtils.showShort(response.getException().getMessage());
                            }
                        });
            }
        });
    }


}
