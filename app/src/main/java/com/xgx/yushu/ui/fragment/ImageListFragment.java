package com.xgx.yushu.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.ImageListAdapter;
import com.xgx.yushu.adapter.WyRsListAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.ImageInfo;
import com.xgx.yushu.bean.ResultData;
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

public class ImageListFragment extends BaseEventBusFragment {
    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.lv)
    RecyclerView lv;
    @Bind(R.id.springView)
    SpringView springView;
    private ImageListAdapter menuAdapter;
    private List<ImageInfo> menus;
    private boolean isInitCache = false;
    private FullyGridLayoutManager manager;

    public static ImageListFragment newInstance() {

        Bundle args = new Bundle();

        ImageListFragment fragment = new ImageListFragment();
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
        titlebar.setTitleText("更多图片");
        initToolbarNav(titlebar);

        manager = new FullyGridLayoutManager(getActivity(), 3);
        menus = new ArrayList<>();

        menuAdapter = new ImageListAdapter(menus);
        lv.setNestedScrollingEnabled(false);
        lv.setLayoutManager(manager);
        lv.setAdapter(menuAdapter);
        lv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                computeBoundsBackward(manager.findFirstVisibleItemPosition());
                GPreviewBuilder.from(getActivity())
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(position)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();//启动
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

    /**
     * 查找信息
     *
     * @param list 图片集合
     */
    private ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();

    /**
     * * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mThumbViewInfoList.size(); i++) {
            View itemView = manager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.icon);
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    @Override
    public void initPresenter() {
        //请求参数
        getData();
    }

    private void getData() {
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.getAllImages)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(MyUrl.getAllImages)
                .tag(this).execute(new JsonCallback<MyResponse>() {

            @Override
            public void onSuccess(Response<MyResponse> response) {
                springView.onFinishFreshAndLoad();

                ResultData userData = response.body().data;
                try {
                    menus = ((JSONArray) userData.info).toJavaList(ImageInfo.class);
                    if (menus != null) {
                        for (int i = 0; i < menus.size(); i++) {
                            mThumbViewInfoList.add(new ThumbViewInfo(menus.get(i).getImage_file_path()));
                        }
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
