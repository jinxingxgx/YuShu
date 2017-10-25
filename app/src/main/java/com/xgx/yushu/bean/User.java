package com.xgx.yushu.bean;

import com.blankj.utilcode.util.StringUtils;
import com.xgx.yushu.utils.MyUrl;

import java.io.Serializable;

/**
 * Created by xgx on 2017/9/9 0009 for YuShu
 */

public class User implements Serializable {


    /**
     * user_id : 21
     * user_pwd : 1f3bb75acb619dcea1f38892c7bb35ac
     * user_salt : vAOivYIOSv
     * user_name : 王旭
     * user_name_set : 1
     * user_nickname : null
     * user_floor : 29
     * user_room : 705
     * user_no : A
     * user_sex : 1
     * user_headpic : http://118.89.149.61/data/upload/efdaf55df5625c91126988a7dafb8517.png
     * user_tel : 18762389869
     * user_tel_set : 1
     * user_birthday : 633744002
     * user_status : 1
     * user_check : 1
     */

    private String user_id;
    private String user_pwd;
    private String user_salt;
    private String user_name;
    private int user_name_set;
    private String user_nickname;
    private String user_floor;
    private String user_room;
    private String user_no;
    private String user_sex;
    private String user_headpic;
    private String user_tel;
    private int user_tel_set;
    private int user_birthday_set;
    private long user_birthday;
    private String user_status;
    private String user_check;

    public int getUser_birthday_set() {
        return user_birthday_set;
    }

    public void setUser_birthday_set(int user_birthday_set) {
        this.user_birthday_set = user_birthday_set;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_salt() {
        return user_salt;
    }

    public void setUser_salt(String user_salt) {
        this.user_salt = user_salt;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_name_set() {
        return user_name_set;
    }

    public void setUser_name_set(int user_name_set) {
        this.user_name_set = user_name_set;
    }

    public String getUser_nickname() {
        return user_nickname == null ? "" : user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_floor() {
        return user_floor;
    }

    public void setUser_floor(String user_floor) {
        this.user_floor = user_floor;
    }

    public String getUser_room() {
        return user_room;
    }

    public void setUser_room(String user_room) {
        this.user_room = user_room;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_headpic() {
        return user_headpic;
    }

    public void setUser_headpic(String user_headpic) {
        this.user_headpic = user_headpic;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public int getUser_tel_set() {
        return user_tel_set;
    }

    public void setUser_tel_set(int user_tel_set) {
        this.user_tel_set = user_tel_set;
    }

    public long getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(long user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_check() {
        return user_check;
    }

    public void setUser_check(String user_check) {
        this.user_check = user_check;
    }
}
