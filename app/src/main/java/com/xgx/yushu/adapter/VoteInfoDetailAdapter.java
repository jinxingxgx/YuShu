package com.xgx.yushu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.VoteInfo;
import com.xgx.yushu.widget.CustomGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class VoteInfoDetailAdapter extends BaseQuickAdapter<VoteInfo.VoteOptionBean, BaseViewHolder> {
    public VoteInfoDetailAdapter(List<VoteInfo.VoteOptionBean> data) {
        super(R.layout.vote_option_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, VoteInfo.VoteOptionBean item) {
        helper.setText(R.id.cb, item.getVote_option_desc());
        helper.setText(R.id.numTv, "总投票数 " + item.getCount());
        helper.setChecked(R.id.cb, item.isChecked());
    }


}
