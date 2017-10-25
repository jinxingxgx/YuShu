package com.xgx.yushu.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.permission.InvokeListener;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.xgx.yushu.R;
import com.xgx.yushu.adapter.CommentListAdapter;
import com.xgx.yushu.base.BaseEventBusFragment;
import com.xgx.yushu.bean.CommentInfo;
import com.xgx.yushu.bean.EventCenter;
import com.xgx.yushu.bean.LoginInfomation;
import com.xgx.yushu.bean.ResultData;
import com.xgx.yushu.bean.TransferInfo;
import com.xgx.yushu.presenter.DialogCallback;
import com.xgx.yushu.presenter.JsonCallback;
import com.xgx.yushu.response.MyResponse;
import com.xgx.yushu.utils.MyUrl;
import com.xgx.yushu.widget.FullLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.titlebar.BGATitleBar;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class TransferDetailFragment extends BaseEventBusFragment implements TakePhoto.TakeResultListener, InvokeListener {

    @Bind(R.id.titlebar)
    BGATitleBar titlebar;
    @Bind(R.id.springView)
    SpringView springView;
    @Bind(R.id.editTv)
    EditText editTv;
    @Bind(R.id.shoucangBtn)
    ImageView shoucangBtn;
    @Bind(R.id.fenxiangBtn)
    ImageView fenxiangBtn;
    @Bind(R.id.sendBtn)
    ImageView sendBtn;
    @Bind(R.id.bottomBar)
    LinearLayout bottomBar;
    @Bind(R.id.lv)
    RecyclerView lv;
    private int page;
    private boolean isInitCache;
    private TransferInfo info;
    private List<CommentInfo> commentList;
    private CommentListAdapter commentListAdapter;
    private TextView titleTv;
    private TextView type;
    private CircleImageView avatarTv;
    private TextView nameTv;
    private TextView contentTv;
    private NineGridImageView nglImages;
    private TextView pinlunNumTv;

    public static TransferDetailFragment newInstance(TransferInfo info) {

        Bundle args = new Bundle();
        args.putSerializable("info", info);
        TransferDetailFragment fragment = new TransferDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (EventCenter.REFRESH_TRANSFER_FRAGMENT == eventCenter.getEventCode()) {
            //getData(info.getTransfer_id());
        }
    }

    @Override
    public boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_transfer_detail;
    }

    @Override
    public void initView() {
        titlebar.setTitleText("详情");
        initToolbarNav(titlebar);
        setCommentAdapter();
        setSpringView();
    }

    @Override
    public void initPresenter() {
        info = (TransferInfo) getArguments().getSerializable("info");
        if (info != null) {
            nameTv.setText(info.getUser_name());
            Glide.with(this).load(MyUrl.baseIp + info.getUser_headpic()).error(R.mipmap.head_me).into(avatarTv);
            titleTv.setText(info.getTransfer_title());
            contentTv.setText(info.getTransfer_desc());
            type.setText("1".equals(info.getTransfer_type()) ? "馈赠" : "置换");
            //请求参数
            getData();
            setNineImage();
        }

    }

    private void setCommentAdapter() {
        commentList = new ArrayList<>();
        commentListAdapter = new CommentListAdapter(commentList);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.transfer_header_view, null);
        titleTv = (TextView) view.findViewById(R.id.titleTv);
        type = (TextView) view.findViewById(R.id.type);
        avatarTv = (CircleImageView) view.findViewById(R.id.avatarTv);
        nameTv = (TextView) view.findViewById(R.id.nameTv);
        pinlunNumTv = (TextView) view.findViewById(R.id.pinlunNumTv);
        contentTv = (TextView) view.findViewById(R.id.contentTv);
        nglImages = (NineGridImageView) view.findViewById(R.id.ngl_images);
        pinlunNumTv = (TextView) view.findViewById(R.id.pinlunNumTv);
        commentListAdapter.setHeaderView(view);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(linearLayout);
        lv.setAdapter(commentListAdapter);
    }

    private void setSpringView() {
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

    private void setNineImage() {
        final NineGridImageView<String> mNglContent = nglImages;
        final ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
        mNglContent.setAdapter(mAdapter);
        mNglContent.setItemImageClickListener(new ItemImageClickListener<String>() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                Log.d("onItemImageClick", list.get(index));
                computeBoundsBackward(list, mNglContent, mThumbViewInfoList);//组成数据
                GPreviewBuilder.from(getActivity())
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(index)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();//启动
            }
        });
        if (EmptyUtils.isNotEmpty(info.getImages()) && info.getImages().size() > 0) {
            List<String> photos = new ArrayList<>();
            for (int i = 0; i < info.getImages().size(); i++) {
                photos.add(info.getImages().get(i).getUploadimage_path_small());
            }
            mNglContent.setImagesData(photos);
            mNglContent.setVisibility(View.VISIBLE);

        } else {
            mNglContent.setVisibility(View.GONE);
        }
    }

    /**
     * 查找信息
     *
     * @param list               图片集合
     * @param mNglContent
     * @param mThumbViewInfoList
     */
    private void computeBoundsBackward(List<String> list, NineGridImageView<String> mNglContent, ArrayList<ThumbViewInfo> mThumbViewInfoList) {
        ThumbViewInfo item;
        mThumbViewInfoList.clear();
        for (int i = 0; i < mNglContent.getChildCount(); i++) {
            View itemView = mNglContent.getChildAt(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView;
                thumbView.getGlobalVisibleRect(bounds);
            }
            item = new ThumbViewInfo(list.get(i));
            item.setBounds(bounds);
            mThumbViewInfoList.add(item);
        }

    }

    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            Glide.with(context).load(s).placeholder(R.drawable.bg_first).into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
            //  Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();

        }
    };

    private void getData() {
        OkGo.<MyResponse>post(MyUrl.baseUrl)
                .params("service", MyUrl.getCommentList)
                .params("page", page + "")
                .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                .params("type", "transfer")
                .params("item_id", info.getTransfer_id())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .cacheKey(MyUrl.getCommentList)
                .tag(this).execute(new JsonCallback<MyResponse>() {

            @Override
            public void onSuccess(Response<MyResponse> response) {
                springView.onFinishFreshAndLoad();
                pinlunNumTv.setText("评论" + info.getTransfer_comment_count());
                ResultData userData = response.body().data;
                try {
                    if (page == 1) {
                        commentList.clear();
                        commentList = ((JSONArray) userData.info).toJavaList(CommentInfo.class);
                    } else {
                        commentList.addAll(((JSONArray) userData.info).toJavaList(CommentInfo.class));
                    }
                } catch (Exception e) {

                }
                commentListAdapter.setNewData(commentList);
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


    @OnClick({R.id.shoucangBtn, R.id.fenxiangBtn, R.id.sendBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shoucangBtn:

                break;
            case R.id.fenxiangBtn:
                break;
            case R.id.sendBtn:
                if (StringUtils.isEmpty(editTv.getText().toString())) {
                    ToastUtils.showShort("请输入评论内容");
                    return;
                }
                OkGo.<MyResponse>post(MyUrl.baseUrl)
                        .params("service", MyUrl.doComment)
                        .params("user_id", LoginInfomation.getInstance().getUser().getUser_id())
                        .params("comment_type", "transfer")
                        .params("comment_item_id", info.getTransfer_id())
                        .params("comment_desc", editTv.getText().toString())

                        .tag(this).execute(new DialogCallback<MyResponse>(getActivity()) {

                    @Override
                    public void onSuccess(Response<MyResponse> response) {
                        springView.onFinishFreshAndLoad();
                        ToastUtils.showShort("评论成功");
                        editTv.setText("");
                        page = 1;
                        getData();
                        hideSoftInput();
                    }

                    @Override
                    public void onError(Response<MyResponse> response) {
                        super.onError(response);
                        springView.onFinishFreshAndLoad();
                    }
                });
                break;
        }
    }
}
