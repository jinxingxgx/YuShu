package com.xgx.yushu.bean;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class HomeMenuEntity {
    private String name = "";
    private int iconRes;

    public HomeMenuEntity(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
