package com.xgx.yushu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.ImageInfo;
import com.xgx.yushu.bean.VideoInfo;

import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {
    public VideoListAdapter(List<VideoInfo> data) {
        super(R.layout.adapter_wyrs_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        Glide.with(mContext).load(item.getVideo_logo_path_small()).error(R.mipmap.head_me).into((ImageView) helper.getView(R.id.icon));
        helper.setText(R.id.nameTv, item.getVideo_title());
        helper.setVisible(R.id.contentTv, false);
    }
}
