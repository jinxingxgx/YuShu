package com.xgx.yushu.bean;

import com.alibaba.fastjson.JSONObject;
import com.xgx.yushu.utils.UnicodeUtils;

/**
 * Created by xgx on 2017/9/9 0009 for YuShu
 */

public class ResultData {
    public int code;
    public String msg;
    public String url;
    public Object info;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
