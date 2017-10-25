package com.xgx.yushu.bean;

import com.xgx.yushu.utils.MyUrl;

import java.util.List;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class PostInfo {


    private String post_id;
    private String post_title;
    private String post_addtime;
    private String post_type;
    private String user_id;
    private String user_name;
    private String user_headpic;
    private String post_praise_count;
    private String post_comment_count;
    private List<ImagesBean> images;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_addtime() {
        return post_addtime;
    }

    public void setPost_addtime(String post_addtime) {
        this.post_addtime = post_addtime;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
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

    public String getPost_praise_count() {
        return post_praise_count;
    }

    public void setPost_praise_count(String post_praise_count) {
        this.post_praise_count = post_praise_count;
    }

    public String getPost_comment_count() {
        return post_comment_count;
    }

    public void setPost_comment_count(String post_comment_count) {
        this.post_comment_count = post_comment_count;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
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
