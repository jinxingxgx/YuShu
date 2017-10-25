package com.xgx.yushu.bean;

/**
 * Created by CWJ on 2017/3/28.
 */

public class EventCenter<T> {
    public static int STRAT_FRAGMENT = 0;//启动fragment
    public static int REFRESH_TRANSFER_FRAGMENT = 1;//刷新闲置转让页面
    public static int REFRESH_VOTE_FRAGMENT = 2;//刷新投票页面
    public static int REFRESH_Setting_FRAGMENT = 3;//刷新个人界面
    public static int REFRESH_PUSH_FRAGMENT = 4;//刷新最新动态

    private T data;
    private int eventCode;

    public EventCenter(int eventCode) {
        this(eventCode, null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = -1;
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public T getData() {
        return this.data;
    }
}