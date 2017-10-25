package com.xgx.yushu.bean;

import com.blankj.utilcode.util.EmptyUtils;

/**
 * Created by xgx on 2017/9/9 0009 for YuShu
 */

public class LoginInfomation {
    private static LoginInfomation instance;
    private User user;

    public synchronized static LoginInfomation getInstance() {
        if (instance == null) {
            instance = new LoginInfomation();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public boolean isLogin() {
        return EmptyUtils.isNotEmpty(user);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
