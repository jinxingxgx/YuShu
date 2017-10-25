package com.xgx.yushu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.FloorInfo;
import com.xgx.yushu.bean.RoomInfo;

import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class RoomListAdapter extends BaseQuickAdapter<RoomInfo, BaseViewHolder> {
    public RoomListAdapter(List<RoomInfo> data) {
        super(R.layout.adapter_room_list, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, RoomInfo item) {
        if (item.getActivate_count() > 0) {
            helper.getView(R.id.roomNameTv).setActivated(true);
        } else {
            helper.getView(R.id.roomNameTv).setActivated(false);
        }
        if (item.getActivate_count() == item.getUser_count()) {
            helper.getView(R.id.roomNumTv).setActivated(true);
        } else {
            helper.getView(R.id.roomNumTv).setActivated(false);
        }
        helper.setText(R.id.roomNameTv, item.getUser_room());
        helper.setText(R.id.roomNumTv, item.getActivate_count() + "/" + item.getUser_count());
    }
}
