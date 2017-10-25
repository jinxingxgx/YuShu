package com.xgx.yushu.bean;

/**
 * Created by xgx on 2017/9/16 0016 for YuShu
 */

public class RoomInfo {

    /**
     * user_room : 101
     * user_count : 2
     * activate_count : 1
     */

    private String user_room;
    private int user_count;
    private int activate_count;

    public String getUser_room() {
        return user_room;
    }

    public void setUser_room(String user_room) {
        this.user_room = user_room;
    }

    public int getUser_count() {
        return user_count;
    }

    public void setUser_count(int user_count) {
        this.user_count = user_count;
    }

    public int getActivate_count() {
        return activate_count;
    }

    public void setActivate_count(int activate_count) {
        this.activate_count = activate_count;
    }
}
