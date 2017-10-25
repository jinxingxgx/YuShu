package com.xgx.yushu.bean;

import com.xgx.yushu.utils.MyUrl;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class ImageInfo {

    /**
     * image_id : 1
     * image_title : sdfdf
     * image_file_path : /data/upload/avatar/59b0abcfe0e34name1.jpg
     * image_file_path_small : /data/upload/avatar/59b0abcfe0e34name1.jpg
     */

    private String image_id;
    private String image_title;
    private String image_file_path;
    private String image_file_path_small;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getImage_file_path() {
        return MyUrl.baseIp + image_file_path;
    }

    public void setImage_file_path(String image_file_path) {
        this.image_file_path = image_file_path;
    }

    public String getImage_file_path_small() {
        return MyUrl.baseIp + image_file_path_small;
    }

    public void setImage_file_path_small(String image_file_path_small) {
        this.image_file_path_small = image_file_path_small;
    }
}
