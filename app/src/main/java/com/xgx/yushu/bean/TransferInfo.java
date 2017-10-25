package com.xgx.yushu.bean;

import com.xgx.yushu.utils.MyUrl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class TransferInfo implements Serializable {

    /**
     * transfer_id : 23
     * transfer_title : 我会一直
     * transfer_addtime : 1505470812
     * transfer_type : 2
     * user_id : 21
     * user_name : 21705王旭
     * user_headpic : /data/upload/0f8916b4b4fee01d1167bb8a53b8bff8.png
     * transfer_praise_count : 0
     * transfer_comment_count : 0
     * images : [{"uploadimage_id":"46","uploadimage_path":"/data/upload/68b1703c28ba0d4101638954d15e5984.png","uploadimage_path_small":"/data/upload/68b1703c28ba0d4101638954d15e5984.png"},{"uploadimage_id":"47","uploadimage_path":"/data/upload/e8a90f8bedcb696fa3a727a739054fd7.png","uploadimage_path_small":"/data/upload/e8a90f8bedcb696fa3a727a739054fd7.png"}]
     */

    private String transfer_id;
    private String transfer_title;
    private String transfer_addtime;
    private String transfer_type;
    private String transfer_desc;
    private String user_id;
    private String user_name;
    private String user_headpic;
    private String transfer_praise_count;
    private String transfer_comment_count;
    private List<ImagesBean> images;

    public String getTransfer_desc() {
        return transfer_desc;
    }

    public void setTransfer_desc(String transfer_desc) {
        this.transfer_desc = transfer_desc;
    }

    public String getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(String transfer_id) {
        this.transfer_id = transfer_id;
    }

    public String getTransfer_title() {
        return transfer_title;
    }

    public void setTransfer_title(String transfer_title) {
        this.transfer_title = transfer_title;
    }

    public String getTransfer_addtime() {
        return transfer_addtime;
    }

    public void setTransfer_addtime(String transfer_addtime) {
        this.transfer_addtime = transfer_addtime;
    }

    public String getTransfer_type() {
        return transfer_type;
    }

    public void setTransfer_type(String transfer_type) {
        this.transfer_type = transfer_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_headpic() {
        return user_headpic;
    }

    public void setUser_headpic(String user_headpic) {
        this.user_headpic = user_headpic;
    }

    public String getTransfer_praise_count() {
        return transfer_praise_count;
    }

    public void setTransfer_praise_count(String transfer_praise_count) {
        this.transfer_praise_count = transfer_praise_count;
    }

    public String getTransfer_comment_count() {
        return transfer_comment_count;
    }

    public void setTransfer_comment_count(String transfer_comment_count) {
        this.transfer_comment_count = transfer_comment_count;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean implements Serializable {
        /**
         * uploadimage_id : 46
         * uploadimage_path : /data/upload/68b1703c28ba0d4101638954d15e5984.png
         * uploadimage_path_small : /data/upload/68b1703c28ba0d4101638954d15e5984.png
         */

        private String uploadimage_id;
        private String uploadimage_path;
        private String uploadimage_path_small;

        public String getUploadimage_id() {
            return uploadimage_id;
        }

        public void setUploadimage_id(String uploadimage_id) {
            this.uploadimage_id = uploadimage_id;
        }

        public String getUploadimage_path() {
            return MyUrl.baseIp + uploadimage_path;
        }

        public void setUploadimage_path(String uploadimage_path) {
            this.uploadimage_path = uploadimage_path;
        }

        public String getUploadimage_path_small() {
            return MyUrl.baseIp + uploadimage_path_small;
        }

        public void setUploadimage_path_small(String uploadimage_path_small) {
            this.uploadimage_path_small = uploadimage_path_small;
        }
    }
}
