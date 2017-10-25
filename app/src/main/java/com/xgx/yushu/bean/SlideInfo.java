package com.xgx.yushu.bean;

import com.xgx.yushu.utils.MyUrl;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class SlideInfo {

    /**
     * slide_id : 5
     * slide_title : 01
     * slide_image_path : /data/upload/avatar/59afd5e55de44name1.jpg
     */

    private String slide_id;
    private String slide_title;
    private String slide_image_path;

    public String getSlide_id() {
        return slide_id;
    }

    public void setSlide_id(String slide_id) {
        this.slide_id = slide_id;
    }

    public String getSlide_title() {
        return slide_title;
    }

    public void setSlide_title(String slide_title) {
        this.slide_title = slide_title;
    }

    public String getSlide_image_path() {
        return MyUrl.baseIp + slide_image_path;
    }

    public void setSlide_image_path(String slide_image_path) {
        this.slide_image_path = slide_image_path;
    }
}
