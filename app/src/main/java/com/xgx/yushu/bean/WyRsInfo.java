package com.xgx.yushu.bean;

import com.xgx.yushu.utils.MyUrl;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class WyRsInfo {

    /**
     * personnel_id : 4
     * personnel_name : we
     * personnel_headpic : /data/upload/avatar/59bb321bb1242name1.jpg
     * personnel_duty : ads
     */

    private String personnel_id;
    private String personnel_name;
    private String personnel_headpic;
    private String personnel_duty;

    public String getPersonnel_id() {
        return personnel_id;
    }

    public void setPersonnel_id(String personnel_id) {
        this.personnel_id = personnel_id;
    }

    public String getPersonnel_name() {
        return personnel_name;
    }

    public void setPersonnel_name(String personnel_name) {
        this.personnel_name = personnel_name;
    }

    public String getPersonnel_headpic() {
        return MyUrl.baseIp + personnel_headpic;
    }

    public void setPersonnel_headpic(String personnel_headpic) {
        this.personnel_headpic = personnel_headpic;
    }

    public String getPersonnel_duty() {
        return personnel_duty;
    }

    public void setPersonnel_duty(String personnel_duty) {
        this.personnel_duty = personnel_duty;
    }
}
