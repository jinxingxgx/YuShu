package com.xgx.yushu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.xgx.yushu.R;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.StartBrotherEvent;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.ui.LoginActivity;
import com.xgx.yushu.utils.Constact;
import com.xgx.yushu.utils.GlideCircleTransform;
import com.xgx.yushu.utils.MyUrl;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class MeFragment extends BaseEventBusFragment {
    @Bind(R.id.exitBtn)
    Button exitBtn;
    @Bind(R.id.callKefuBtn)
    SuperTextView callKefuBtn;
    @Bind(R.id.yijianBtn)
    SuperTextView yijianBtn;
    @Bind(R.id.aboutBtn)
    SuperTextView aboutBtn;
    @Bind(R.id.haopinBtn)
    SuperTextView haopinBtn;
    @Bind(R.id.settingIv)
    ImageView settingIv;
    @Bind(R.id.headIconIv)
    CircleImageView headIconIv;
    @Bind(R.id.nameTv)
    TextView nameTv;
    @Bind(R.id.contentTv)
    TextView contentTv;
    @Bind(R.id.headLayout)
    RelativeLayout headLayout;
    @Bind(R.id.roomTv)
    TextView roomTv;
    @Bind(R.id.mySendTv)
    TextView mySendTv;
    @Bind(R.id.sendNumTv)
    TextView sendNumTv;
    @Bind(R.id.myShouCangTv)
    TextView myShouCangTv;
    @Bind(R.id.souCangNumTv)
    TextView souCangNumTv;
    @Bind(R.id.myShouCangLayout)
    LinearLayout myShouCangLayout;
    @Bind(R.id.myPinLunTv)
    TextView myPinLunTv;
    @Bind(R.id.pinlunNumTv)
    TextView pinlunNumTv;
    @Bind(R.id.myPinLunLayout)
    LinearLayout myPinLunLayout;
    @Bind(R.id.myDianZanTv)
    TextView myDianZanTv;
    @Bind(R.id.dianZanTv)
    TextView dianZanTv;
    @Bind(R.id.myDianZanLayout)
    LinearLayout myDianZanLayout;
    @Bind(R.id.midLayout)
    LinearLayout midLayout;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (EventCenter.REFRESH_Setting_FRAGMENT == eventCenter.getEventCode()) {
            initPresenter();
        }
    }

    @Override
    public boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_me;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {
        User user = LoginInfomation.getInstance().getUser();
        if (EmptyUtils.isNotEmpty(user)) {
            Glide.with(getActivity()).load(MyUrl.baseIp + LoginInfomation.getInstance().getUser().getUser_headpic()).error(R.mipmap.head_me).transform(new GlideCircleTransform(getActivity())).into(headIconIv);
            nameTv.setText(user.getUser_name());
            roomTv.setVisibility(View.VISIBLE);
            roomTv.setText(user.getUser_floor() + "栋" + user.getUser_room());
        } else {
            nameTv.setText("请登陆");
            roomTv.setVisibility(View.GONE);
        }

    }


    @OnClick({R.id.settingIv, R.id.callKefuBtn, R.id.yijianBtn, R.id.aboutBtn, R.id.haopinBtn, R.id.exitBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settingIv:
                if (LoginInfomation.getInstance().isLogin()) {
                    EventBus.getDefault().post(new StartBrotherEvent(SettingFragment.newInstance()));
                } else {
                    ToastUtils.showShort("请登陆");
                }
                break;
            case R.id.callKefuBtn:
                break;
            case R.id.yijianBtn:
                break;
            case R.id.aboutBtn:

                break;
            case R.id.haopinBtn:
                break;
            case R.id.exitBtn:
                SPUtils.getInstance().remove(Constact.loginUserSp);
                LoginInfomation.getInstance().setUser(null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();

                break;
        }
    }


//    @Override
//    protected void onEventComming(EventCenter eventCenter) {
//
//    }
//
//    @Override
//    public boolean isBindEventBusHere() {
//        return false;
//    }
//
//    @Override
//    public int getLayoutRes() {
//        return R.layout.layout_me;
//    }
//
//    @Override
//    public void initView() {
//
//    }
//
//    @Override
//    public void initPresenter() {
//
//    }
}
