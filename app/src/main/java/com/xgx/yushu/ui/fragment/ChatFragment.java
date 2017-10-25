package com.xgx.yushu.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.FloorListAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.FloorInfo;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.StartBrotherEvent;
import com.xgx.yushu.presenter.JsonCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.widget.FullyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class ChatFragment extends BaseEventBusFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.banner_main_default)
    BGABanner bannerMainDefault;
    @Bind(R.id.lv)
    RecyclerView menuView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.bodyLayout)
    RelativeLayout bodyLayout;
    private FloorListAdapter menuAdapter;
    private List<FloorInfo> menus;
    private boolean isInitCache = false;

    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
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
        return R.layout.layout_chat;
    }

    @Override
    public void initView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3);
        menus = new ArrayList<>();
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        menuAdapter = new FloorListAdapter(menus);
        menuView.setNestedScrollingEnabled(false);
        menuView.setLayoutManager(manager);
        menuView.setAdapter(menuAdapter);
        menuView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //     Toast.makeText(getContext(), "点击了：" + menuAdapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new StartBrotherEvent(RoomInfoFragment.newInstance(menuAdapter.getItem(position).getUser_floor())));
            }
        });
    }

    @Override
    public void initPresenter() {
        //请求参数
        getData();
    }

    private void getData() {
        swipeLayout.setRefreshing(true);
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.getFloorInfo)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(MyUrl.getFloorInfo)
                .tag(this).execute(new JsonCallback<MyResponse>() {

            @Override
            public void onSuccess(Response<MyResponse> response) {
                swipeLayout.setRefreshing(false);

                ResultData userData = response.body().data;
                try {
                    menus = ((JSONArray) userData.info).toJavaList(FloorInfo.class);
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
                swipeLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();

            }
        }, 200);
    }
}
