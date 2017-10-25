package com.xgx.yushu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.xgx.yushu.bean.TransferInfo;
import com.xgx.yushu.bean.VoteInfo;
import com.xgx.yushu.widget.CustomGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class VoteInfoListAdapter extends BaseQuickAdapter<VoteInfo, BaseViewHolder> {
    public VoteInfoListAdapter(List<VoteInfo> data) {
        super(R.layout.adapter_voteinfo_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, VoteInfo item) {
        helper.addOnClickListener(R.id.cardView);
        CustomGroup customGroup = helper.getView(R.id.customGroup);
        customGroup.removeAllViews();
        if (item.getPraise_user_list() != null) {
            for (int i = 0; i < item.getPraise_user_list().size(); i++) {
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.custom_group_item, customGroup, false);
                ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
                Glide.with(mContext).load(item.getPraise_user_list().get(i).getUser_headpic()).into(imageView);
                customGroup.addView(inflate);
            }
        }
        helper.setText(R.id.titleTv, item.getVote_title());
        helper.setImageResource(R.id.status_icon, 0 == item.getVote_status() ? R.drawable.icon_going : R.drawable.icon_gone);
        try {
            helper.setText(R.id.endTimeTv, "截止：" + TimeUtils.millis2String(item.getVote_endtime(), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())));
        } catch (Exception e) {
        }

    }


}
