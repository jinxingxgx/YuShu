package com.xgx.yushu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.ui.LoginActivity;
import com.xgx.yushu.utils.Constact;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.utils.UnicodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class SettingEditFragment extends BaseEventBusFragment {

    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.edit)
    EditText edit;

    public static SettingEditFragment newInstance(String name, String type) {

        Bundle args = new Bundle();
        args.putString("content", name);
        args.putString("type", type);
        SettingEditFragment fragment = new SettingEditFragment();
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
        return R.layout.fragment_setting_edit;
    }

    @Override
    public void initView() {
        initToolbarNav(titlebar);
        titlebar.setRightText("完成");
        if ("user_nickname".equals(getArguments().getString("type"))) {
            titlebar.setTitleText("修改昵称");
        } else if ("user_name".equals(getArguments().getString("type"))) {
            titlebar.setTitleText("修改真实姓名");
        }
        edit.setText(getArguments().getString("content", ""));
    }

    @Override
    protected void onTitleClickRightCtv() {
        super.onTitleClickRightCtv();
        if (EmptyUtils.isEmpty(edit.getText().toString())) {
            ToastUtils.showShort("修改条件不能为空");
            return;
        }
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.updateUserInfo)
                .params(getArguments().getString("type"), edit.getText().toString())
                .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                .execute(new DialogCallback<MyResponse>(getActivity()) {
                    @Override
                    public void onSuccess(Response<MyResponse> response) {
                        ToastUtils.showShort("更改成功");
                        EventBus.getDefault().post(new EventCenter(EventCenter.REFRESH_Setting_FRAGMENT));
                        hideSoftInput();
                        pop();
                    }

                    @Override
                    public void onError(Response<MyResponse> response) {
                        super.onError(response);
                        ToastUtils.showShort(response.getException().getMessage());
                    }
                });

    }

    @Override
    public void initPresenter() {
    }


}
