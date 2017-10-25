package com.xgx.yushu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.CommentInfo;
import com.xgx.yushu.bean.TransferInfo;
import com.xgx.yushu.utils.MyUrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class CommentListAdapter extends BaseQuickAdapter<CommentInfo, BaseViewHolder> {
    public CommentListAdapter(List<CommentInfo> data) {
        super(R.layout.adapter_comment_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CommentInfo item) {
        helper.setText(R.id.nameTv, item.getUser_name());
        Glide.with(mContext).load(MyUrl.baseIp + item.getUser_headpic()).error(R.mipmap.head_me).into((ImageView) helper.getView(R.id.avatarTv));
        helper.setText(R.id.contentTv, item.getComment_desc());
        helper.setText(R.id.timeTv, TimeUtils.millis2String(item.getComment_addtime(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())));
    }


}
