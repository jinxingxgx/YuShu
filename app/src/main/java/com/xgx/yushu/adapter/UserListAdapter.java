package com.xgx.yushu.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.User;
import com.xgx.yushu.bean.WyRsInfo;
import com.xgx.yushu.utils.MyUrl;

import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class UserListAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    public UserListAdapter(List<User> data) {
        super(R.layout.adapter_wyrs_item, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, User item) {
        Glide.with(mContext).load(MyUrl.baseIp + item.getUser_headpic()).error(R.mipmap.head_me).into((ImageView) helper.getView(R.id.icon));
        helper.setText(R.id.nameTv, item.getUser_name());
        helper.setVisible(R.id.contentTv,false);
    }
}
