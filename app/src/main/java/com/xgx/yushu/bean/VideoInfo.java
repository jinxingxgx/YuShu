package com.xgx.yushu.bean;

import com.xgx.yushu.utils.MyUrl;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class VideoInfo {

    /**
     * video_id : 2
     * video_title : null
     * video_logo_path : /data/upload/avatar/59b0ae454b19dname1.jpg
     * video_logo_path_small : /data/upload/avatar/small_59b0ae454b19dname1.jpg
     */

    private String video_id;
    private String video_title;
    private String video_logo_path;
    private String video_logo_path_small;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_title() {
        return video_title == null ? "" : video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_logo_path() {
        return MyUrl.baseIp + video_logo_path;
    }

    public void setVideo_logo_path(String video_logo_path) {
        this.video_logo_path = video_logo_path;
    }

    public String getVideo_logo_path_small() {
        return MyUrl.baseIp + video_logo_path_small;
    }

    public void setVideo_logo_path_small(String video_logo_path_small) {
        this.video_logo_path_small = video_logo_path_small;
    }
}
