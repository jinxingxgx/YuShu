package com.xgx.yushu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.HomeMenuEntity;

import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class HomeMenuAdapter extends BaseQuickAdapter<HomeMenuEntity, BaseViewHolder> {
    public HomeMenuAdapter(List<HomeMenuEntity> data) {
        super(R.layout.adapter_home_menu, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, HomeMenuEntity item) {
        helper.setText(R.id.title, item.getName());
        Glide.with(mContext).load(item.getIconRes()).error(R.drawable.no_result).into((ImageView) helper.getView(R.id.icon));
    }
}
