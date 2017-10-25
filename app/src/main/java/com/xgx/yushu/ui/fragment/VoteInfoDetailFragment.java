package com.xgx.yushu.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.VoteInfoDetailAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.VoteInfo;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.presenter.JsonCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.widget.FullLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class VoteInfoDetailFragment extends BaseEventBusFragment {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.status_icon)
    ImageView statusIcon;
    @Bind(R.id.titleTv)
    TextView titleTv;
    @Bind(R.id.contentTv)
    TextView contentTv;
    @Bind(R.id.cardView)
    CardView cardView;
    @Bind(R.id.totalNumTv)
    SuperTextView totalNumTv;
    @Bind(R.id.endTimeTv)
    TextView endTimeTv;
    @Bind(R.id.detailLayout)
    RecyclerView detailLayout;
    @Bind(R.id.voteBtn)
    SuperButton voteBtn;
    private boolean isInitCache = false;
    private int page = 1;
    private List<VoteInfo.VoteOptionBean> voteOptions;
    private VoteInfoDetailAdapter voteOptionAdapter;
    private VoteInfo info;
    private String selectVoteOptionId;

    public static VoteInfoDetailFragment newInstance(String voteId) {

        Bundle args = new Bundle();
        args.putString("vote_id", voteId);
        VoteInfoDetailFragment fragment = new VoteInfoDetailFragment();
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
        return R.layout.fragment_vote_detail;
    }

    @Override
    public void initView() {
        voteBtn.setEnabled(false);
        voteOptions = new ArrayList<>();
        titlebar.setTitleText("民意投票");
        initToolbarNav(titlebar);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        voteOptionAdapter = new VoteInfoDetailAdapter(voteOptions);
        detailLayout.setLayoutManager(manager);
        detailLayout.setAdapter(voteOptionAdapter);
        detailLayout.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean b = voteOptionAdapter.getData().get(position).isChecked();
                for (int i = 0; i < voteOptionAdapter.getData().size(); i++) {
                    if (voteOptionAdapter.getData().get(i).getVote_option_id().equals(voteOptionAdapter.getData().get(position).getVote_option_id())) {
                        voteOptionAdapter.getData().get(position).setChecked(!b);
                    } else {
                        if (!b) {
                            voteOptionAdapter.getData().get(i).setChecked(false);
                        }
                    }

                }
                if (!b) {
                    voteBtn.setEnabled(true);
                    selectVoteOptionId = voteOptionAdapter.getData().get(position).getVote_option_id();
                } else {
                    voteBtn.setEnabled(false);
                }
                voteOptionAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initPresenter() {
        //请求参数
        getData();
    }

    private void getData() {
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.getVoteInfoById)
                .params("user_id", LoginInfomation.getInstance().getUser().getUser_id() + "")
                .params("vote_id", getArguments().getString("vote_id", ""))
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(MyUrl.getVoteInfoById)
                .tag(this).execute(new JsonCallback<MyResponse>() {

            @Override
            public void onSuccess(Response<MyResponse> response) {
                ResultData userData = response.body().data;
                try {
                    info = ((JSONObject) userData.info).toJavaObject(VoteInfo.class);
                    titleTv.setText(info.getVote_title() + "");
                    statusIcon.setImageResource(0 == info.getVote_status() ? R.drawable.icon_going : R.drawable.icon_gone);
                    contentTv.setText(info.getVote_desc() + "");
                    totalNumTv.setRightString("总投票数" + info.getTotalcount());
                    endTimeTv.setText("截止日期 " + TimeUtils.millis2String(info.getVote_endtime(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())));
                    voteOptions = info.getVote_option();
                    voteOptionAdapter.setNewData(voteOptions);
                } catch (Exception e) {

                }

            }

            @Override
            public void onCacheSuccess(Response<MyResponse> response) {
                super.onCacheSuccess(response);
                if (!isInitCache) {
                    onSuccess(response);
                    isInitCache = true;
                }
            }

            @Override
            public void onError(Response<MyResponse> response) {
                super.onError(response);
            }
        });
    }

    private boolean isCanClick = false;

    @OnClick(R.id.voteBtn)
    public void onClick() {
        if (info == null) {
            ToastUtils.showShort("无法获取投票详情");
            return;
        }
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.doVote)
                .params("user_id", LoginInfomation.getInstance().getUser().getUser_id() + "")
                .params("vote_option_id", selectVoteOptionId)
                .params("vote_id", getArguments().getString("vote_id", ""))
                .tag(this).execute(new DialogCallback<MyResponse>(getActivity()) {

            @Override
            public void onSuccess(Response<MyResponse> response) {
                ResultData userData = response.body().data;
                ToastUtils.showShort(userData.getMsg());
                EventBus.getDefault().post(new EventCenter(EventCenter.REFRESH_VOTE_FRAGMENT));
                pop();
            }


            @Override
            public void onError(Response<MyResponse> response) {
                super.onError(response);
                ToastUtils.showShort(response.getException().getMessage());

            }
        });
    }
}
