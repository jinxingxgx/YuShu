package com.xgx.yushu.adapter;

import android.graphics.drawable.Icon;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.FloorInfo;
import com.xgx.yushu.bean.WyRsInfo;
import com.xgx.yushu.utils.MyUrl;

import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class WyRsListAdapter extends BaseQuickAdapter<WyRsInfo, BaseViewHolder> {
    public WyRsListAdapter(List<WyRsInfo> data) {
        super(R.layout.adapter_wyrs_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, WyRsInfo item) {
        Glide.with(mContext).load( item.getPersonnel_headpic()).error(R.mipmap.head_me).into((ImageView) helper.getView(R.id.icon));
        helper.setText(R.id.nameTv, item.getPersonnel_name());
        helper.setText(R.id.contentTv, item.getPersonnel_duty());
    }
}
