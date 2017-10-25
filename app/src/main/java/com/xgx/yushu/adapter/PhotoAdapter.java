package com.xgx.yushu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.ImageInfo;

import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class PhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PhotoAdapter(List<String> data) {
        super(R.layout.adapter_wyrs_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item).error(R.drawable.bg_head).into((ImageView) helper.getView(R.id.icon));
        helper.setVisible(R.id.contentTv, false);
    }
}
