package com.xgx.yushu.bean;

/**
 * Created by xgx on 2017/9/25 0025 for YuShu
 */

public class CommentInfo {

    /**
     * user_id : 21
     * user_name : 21705王旭哈
     * user_headpic : /data/upload/0cebb4aeaeb8ce23328b8f1efb5f792f.jpg
     * comment_desc : 测试
     * comment_addtime : 1506344331
     */

    private String user_id;
    private String user_name;
    private String user_headpic;
    private String comment_desc;
    private long comment_addtime;

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

    public String getComment_desc() {
        return comment_desc;
    }

    public void setComment_desc(String comment_desc) {
        this.comment_desc = comment_desc;
    }

    public long getComment_addtime() {
        return comment_addtime;
    }

    public void setComment_addtime(long comment_addtime) {
        this.comment_addtime = comment_addtime;
    }
}
