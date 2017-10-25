package com.xgx.yushu.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.HomeMenuAdapter;
import com.xgx.yushu.adapter.ImageListAdapter;
import com.xgx.yushu.adapter.VideoListAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.HomeInfo;
import com.xgx.yushu.bean.HomeMenuEntity;
import com.xgx.yushu.bean.ImageInfo;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.StartBrotherEvent;
import com.xgx.yushu.bean.VideoInfo;
import com.xgx.yushu.presenter.JsonCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.widget.FullyGridLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class HomeFragment extends BaseEventBusFragment implements BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {

    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.home_broadcast)
    TextView homeBroadcast;
    @Bind(R.id.menu_view)
    RecyclerView menuView;
    @Bind(R.id.menu_pic)
    RecyclerView menuPic;
    @Bind(R.id.menu_video)
    RecyclerView menuVideo;
    @Bind(R.id.banner_main_default)
    BGABanner bannerMainDefault;
    @Bind(R.id.morePicTV)
    SuperTextView morePicTV;
    @Bind(R.id.moreVideoTV)
    SuperTextView moreVideoTV;
    private HomeMenuAdapter menuAdapter;
    private boolean isInitCache = false;
    private ImageListAdapter imageAdapter;
    private VideoListAdapter videoAdapter;
    private FullyGridLayoutManager manager;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
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
        return R.layout.layout_home_fragment;
    }

    @Override
    public void initView() {
        //初始化 menu
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 4);
        List<HomeMenuEntity> menus = new ArrayList<>();
        menus.add(new HomeMenuEntity("群主公告", R.mipmap.menu_notice));
        menus.add(new HomeMenuEntity("群聊广场", R.mipmap.menu_luntan));
        menus.add(new HomeMenuEntity("邻里发布", R.mipmap.menu_zhuanrang));
        menus.add(new HomeMenuEntity("物业人事", R.mipmap.menu_person));
        menus.add(new HomeMenuEntity("小区公约", R.mipmap.menu_gongyue));
        menus.add(new HomeMenuEntity("民意投票", R.mipmap.menu_one));
        menus.add(new HomeMenuEntity("装修指南", R.mipmap.menu_zhinan));
        menus.add(new HomeMenuEntity("周边便民", R.mipmap.menu_bianming));
        menuAdapter = new HomeMenuAdapter(menus);
        menuView.setNestedScrollingEnabled(false);

        menuView.setLayoutManager(manager);
        menuView.setAdapter(menuAdapter);
        menuView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        EventBus.getDefault().post(new StartBrotherEvent(H5WebFragment.newInstance(MyUrl.getAdminnotice, menuAdapter.getItem(position).getName())));
                        break;
                    case 2:
                        EventBus.getDefault().post(new StartBrotherEvent(TransferTabFragment.newInstance()));
                        break;
                    case 3:
                        EventBus.getDefault().post(new StartBrotherEvent(WyRsFragment.newInstance()));
                        break;
                    case 4:
                        EventBus.getDefault().post(new StartBrotherEvent(H5WebFragment.newInstance(MyUrl.getConvention_display, menuAdapter.getItem(position).getName())));
                        break;
                    case 5:
                        EventBus.getDefault().post(new StartBrotherEvent(VoteInfoListFragment.newInstance()));
                        break;
                    case 6:
                        EventBus.getDefault().post(new StartBrotherEvent(H5WebFragment.newInstance(MyUrl.getGuide, menuAdapter.getItem(position).getName())));
                        break;

                }

            }
        });
        //设置item之间的间隔
        List<String> imgs = new ArrayList<>();
        List<String> tips = new ArrayList<>();
        imgs.add("http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg");
        tips.add("标题1");
        imgs.add("http://pic28.photophoto.cn/20130731/0036036831383071_b.jpg");
        tips.add("标题2");
        imgs.add("http://img1.3lian.com/2015/a1/43/d/82.jpg");
        tips.add("标题3");
        bannerMainDefault.setDelegate(this);

        bannerMainDefault.setAdapter(this);

        bannerMainDefault.setData(imgs, tips);

        setImageListView();
        setVideoListView();
    }

    private void setVideoListView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3);
        List<VideoInfo> videos = new ArrayList<>();

        videoAdapter = new VideoListAdapter(videos);
        menuVideo.setNestedScrollingEnabled(false);
        menuVideo.setLayoutManager(manager);
        menuVideo.setAdapter(videoAdapter);
        menuVideo.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    private void setImageListView() {
        manager = new FullyGridLayoutManager(getActivity(), 3);
        List<ImageInfo> images = new ArrayList<>();

        imageAdapter = new ImageListAdapter(images);
        menuPic.setNestedScrollingEnabled(false);
        menuPic.setLayoutManager(manager);
        menuPic.setAdapter(imageAdapter);
        menuPic.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                computeBoundsBackward(manager.findFirstVisibleItemPosition());
                GPreviewBuilder.from(getActivity())
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(position)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();//启动
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
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.Index)
                .cacheKey(MyUrl.Index)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallback<MyResponse>() {
                    @Override
                    public void onSuccess(Response<MyResponse> response) {
                        ResultData userData = response.body().data;
                        try {
                            HomeInfo info = ((JSONObject) userData.info).toJavaObject(HomeInfo.class);
                            //设置item之间的间隔
                            List<String> imgs = new ArrayList<>();
                            List<String> tips = new ArrayList<>();
                            for (int i = 0; i < info.getSlidelist().size(); i++) {
                                imgs.add(info.getSlidelist().get(i).getSlide_image_path());
                                tips.add(info.getSlidelist().get(i).getSlide_title());
                            }
                            bannerMainDefault.setData(imgs, tips);
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < info.getBroadcastlist().size(); i++) {
                                sb.append(info.getBroadcastlist().get(i).getBroadcast_title());
                                sb.append(":");
                                sb.append(info.getBroadcastlist().get(i).getBroadcast_desc());
                                sb.append("                           ");
                            }
                            homeBroadcast.setText(sb.toString());
                            homeBroadcast.setSelected(true);
                            for (int i = 0; i < info.getImageList().size(); i++) {
                                mThumbViewInfoList.add(new ThumbViewInfo(info.getImageList().get(i).getImage_file_path()));
                            }
                            imageAdapter.setNewData(info.getImageList());
                            videoAdapter.setNewData(info.getVideoList());
                        } catch (Exception e) {
                            LogUtils.e(e.getMessage());
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<MyResponse> response) {
                        super.onCacheSuccess(response);
                        if (!isInitCache) {
                            isInitCache = true;
                            onSuccess(response);
                        }
                    }
                });
        //获取图片
        //获取视频
        //点击播放视频
    }


    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
        Toast.makeText(banner.getContext(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();

    }


    @OnClick({R.id.morePicTV, R.id.moreVideoTV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.morePicTV:
                EventBus.getDefault().post(new StartBrotherEvent(ImageListFragment.newInstance()));
                break;
            case R.id.moreVideoTV:
                EventBus.getDefault().post(new StartBrotherEvent(VideoListFragment.newInstance()));
                break;
        }
    }
}
