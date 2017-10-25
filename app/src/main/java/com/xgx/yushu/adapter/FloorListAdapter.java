package com.xgx.yushu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.FloorInfo;
import com.xgx.yushu.bean.HomeMenuEntity;

import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class FloorListAdapter extends BaseQuickAdapter<FloorInfo, BaseViewHolder> {
    public FloorListAdapter(List<FloorInfo> data) {
        super(R.layout.adapter_lin_list, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, FloorInfo item) {
        helper.setText(R.id.louNameTv, item.getUser_floor() + "号楼");
        helper.setText(R.id.louNumTv, "--  " + item.getCount() + "人  --");
    }
}
