package com.xgx.yushu.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.permission.InvokeListener;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.melnykov.fab.FloatingActionButton;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.PostListAdapter;
import com.xgx.yushu.adapter.TransferListAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.PostInfo;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.TransferInfo;
import com.xgx.yushu.presenter.JsonCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.MyUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.titlebar.BGATitleBar;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class PostListFragment extends BaseEventBusFragment implements TakePhoto.TakeResultListener, InvokeListener {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.lv)
    RecyclerView lv;
    @Bind(R.id.springView)
    SpringView springView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private PostListAdapter menuAdapter;
    private List<PostInfo> menus;
    private boolean isInitCache = false;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private int page = 1;

    public static PostListFragment newInstance(int i) {

        Bundle args = new Bundle();
        args.putInt("type", i);
        PostListFragment fragment = new PostListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (EventCenter.REFRESH_PUSH_FRAGMENT == eventCenter.getEventCode()) {
            page = 1;
            getData();
        }
    }

    @Override
    public boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_wyrs;
    }

    @Override
    public void initView() {
        titlebar.setVisibility(View.GONE);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        menus = new ArrayList<>();
        menuAdapter = new PostListAdapter(menus);
        lv.setNestedScrollingEnabled(false);
        lv.setLayoutManager(manager);
        lv.setAdapter(menuAdapter);
        fab.setVisibility(View.VISIBLE);
        fab.attachToRecyclerView(lv);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SupportFragment) getParentFragment()).start(PushPostFragment.newInstance(getArguments().getInt("type", 1)));

            }
        });
        lv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();

                    }
                }, 200);
            }

            @Override
            public void onLoadmore() {
                page++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();

                    }
                }, 200);

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
                .params("service", MyUrl.getPostInfo)
                .params("page", page + "")
                .params("post_type", getArguments().getInt("type", 1))
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(MyUrl.getPostInfo)
                .tag(this).execute(new JsonCallback<MyResponse>() {

            @Override
            public void onSuccess(Response<MyResponse> response) {
                springView.onFinishFreshAndLoad();
                ResultData userData = response.body().data;
                try {
                    if (page == 1) {
                        menus.clear();
                        menus = ((JSONArray) userData.info).toJavaList(PostInfo.class);
                    } else {
                        menus.addAll(((JSONArray) userData.info).toJavaList(PostInfo.class));
                    }
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


}
