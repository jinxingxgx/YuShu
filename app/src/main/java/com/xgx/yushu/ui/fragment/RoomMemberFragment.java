package com.xgx.yushu.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.UserListAdapter;
import com.xgx.yushu.adapter.WyRsListAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.bean.WyRsInfo;
import com.xgx.yushu.presenter.JsonCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.widget.FullyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class RoomMemberFragment extends BaseEventBusFragment {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.lv)
    RecyclerView lv;
    @Bind(R.id.springView)
    SpringView springView;
    private UserListAdapter menuAdapter;
    private List<User> menus;
    private boolean isInitCache = false;

    public static RoomMemberFragment newInstance(String user_floor, String user_room) {

        Bundle args = new Bundle();
        args.putString("user_floor", user_floor);
        args.putString("user_room", user_room);
        RoomMemberFragment fragment = new RoomMemberFragment();
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
        return R.layout.fragment_wyrs;
    }

    @Override
    public void initView() {
        titlebar.setTitleText("用户列表");
        initToolbarNav(titlebar);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3);
        menus = new ArrayList<>();

        menuAdapter = new UserListAdapter(menus);
        lv.setNestedScrollingEnabled(false);
        lv.setLayoutManager(manager);
        lv.setAdapter(menuAdapter);
        lv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //     Toast.makeText(getContext(), "点击了：" + menuAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                //  EventBus.getDefault().post(new StartBrotherEvent(RoomInfoFragment.newInstance(menuAdapter.getItem(position).getUser_floor())));
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();

                    }
                }, 200);
            }

            @Override
            public void onLoadmore() {
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
                .params("service", MyUrl.getUserListByRoom)
                .params("user_floor", getArguments().getString("user_floor", ""))
                .params("user_room", getArguments().getString("user_room", ""))
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(MyUrl.getUserListByRoom + getArguments().getString("user_floor", "") + getArguments().getString("user_room", ""))
                .tag(this).execute(new JsonCallback<MyResponse>() {

            @Override
            public void onSuccess(Response<MyResponse> response) {
                springView.onFinishFreshAndLoad();

                ResultData userData = response.body().data;
                try {
                    menus = ((JSONArray) userData.info).toJavaList(User.class);
                } catch (Exception e) {

                }
                menuAdapter.setNewData(menus);
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
                springView.onFinishFreshAndLoad();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
