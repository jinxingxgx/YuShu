package com.xgx.yushu.bean;

import com.google.gson.annotations.SerializedName;
import com.xgx.yushu.utils.MyUrl;

import java.util.List;

/**
 * Created by xgx on 2017/9/22 0022 for YuShu
 */

public class VoteInfo {

    /**
     * vote_id : 5
     * vote_title : 11a
     * vote_desc : 5
     * vote_endtime : 1505283900
     * vote_status : 0
     * praise_user_list : [{"user_headpic":"/data/upload/2ed41f77197c7d605d1ebe7ff6b8c899.png"},{"user_headpic":"/data/upload/efdaf55df5625c91126988a7dafb8517.png"},{"user_headpic":"/data/upload/efdaf55df5625c91126988a7dafb8517.png"},{"user_headpic":"/data/upload/efdaf55df5625c91126988a7dafb8517.png"}]
     * praise_count : 4
     */

    private String vote_id;
    private String vote_title;
    private String vote_desc;
    private long vote_endtime;
    private int vote_status;
    private int praise_count;
    private String vote_option_id;
    private String totalcount;
    private List<VoteOptionBean> vote_option;
    private List<PraiseUserListBean> praise_user_list;

    /**
     * vote_endtime : 1505989800
     * vote_option_id : 52
     * vote_status : 0
     * vote_option : [{"vote_option_id":"51","vote_option_desc":"选项一","count":"0"},{"vote_option_id":"52","vote_option_desc":"选项二","count":"1"},{"vote_option_id":"53","vote_option_desc":"选项三","count":"0"}]
     * totalcount : 1
     */


    public String getVote_id() {
        return vote_id;
    }

    public void setVote_id(String vote_id) {
        this.vote_id = vote_id;
    }

    public String getVote_title() {
        return vote_title;
    }

    public void setVote_title(String vote_title) {
        this.vote_title = vote_title;
    }

    public String getVote_desc() {
        return vote_desc;
    }

    public void setVote_desc(String vote_desc) {
        this.vote_desc = vote_desc;
    }

    public long getVote_endtime() {
        return vote_endtime;
    }

    public void setVote_endtime(long vote_endtime) {
        this.vote_endtime = vote_endtime;
    }

    public int getVote_status() {
        return vote_status;
    }

    public void setVote_status(int vote_status) {
        this.vote_status = vote_status;
    }

    public int getPraise_count() {
        return praise_count;
    }

    public void setPraise_count(int praise_count) {
        this.praise_count = praise_count;
    }

    public List<PraiseUserListBean> getPraise_user_list() {
        return praise_user_list;
    }

    public void setPraise_user_list(List<PraiseUserListBean> praise_user_list) {
        this.praise_user_list = praise_user_list;
    }


    public String getVote_option_id() {
        return vote_option_id;
    }

    public void setVote_option_id(String vote_option_id) {
        this.vote_option_id = vote_option_id;
    }


    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public List<VoteOptionBean> getVote_option() {
        return vote_option;
    }

    public void setVote_option(List<VoteOptionBean> vote_option) {
        this.vote_option = vote_option;
    }

    public static class PraiseUserListBean {
        /**
         * user_headpic : /data/upload/2ed41f77197c7d605d1ebe7ff6b8c899.png
         */

        private String user_headpic;

        public String getUser_headpic() {
            return MyUrl.baseIp + user_headpic;
        }

        public void setUser_headpic(String user_headpic) {
            this.user_headpic = user_headpic;
        }
    }

    public static class VoteOptionBean {
        /**
         * vote_option_id : 51
         * vote_option_desc : 选项一
         * count : 0
         */

        private String vote_option_id;
        private String vote_option_desc;
        private String count;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getVote_option_id() {
            return vote_option_id;
        }

        public void setVote_option_id(String vote_option_id) {
            this.vote_option_id = vote_option_id;
        }

        public String getVote_option_desc() {
            return vote_option_desc;
        }

        public void setVote_option_desc(String vote_option_desc) {
            this.vote_option_desc = vote_option_desc;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
