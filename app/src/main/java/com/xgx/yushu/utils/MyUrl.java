package com.xgx.yushu.utils;

import android.os.Environment;

/**
 * Created by xgx on 2017/9/9 0009 for YuShu
 */

public class MyUrl {
    public static String baseIp = "http://118.89.149.61:88/";
    public static String baseUrl = baseIp + "interface/public/yushuapp/";
    public static String baseDir = Environment.getExternalStorageDirectory() + "YuShu/";
    public static String register = "User.register";
    public static String Index = "Index.getIndexInfo";
    public static String getBaseInfo = "User.getBaseInfo";
    public static String login = "User.login";
    public static String getPersonnelInfo = "Index.getpersonnelInfo";// 物业人事接口
    public static String updateUserInfo = "User.updateUserInfo";// 更改个人信息
    public static String UploadPic = "Upload.Go";// 图片上传接口
    public static String getAllImages = "Index.getAllImages";// 所有图片接口
    public static String getAllVideos = "Index.getAllVideos";// 所有视频接口
    public static String getFloorInfo = "User.getFloorInfo";// 获取楼号列表
    public static String getTransferInfo = "Index.getTransferInfo";// 获取闲置转让列表
    public static String getCommentList = "Index.getCommentList";// 获取评论列表
    public static String doComment = "Index.doComment";// 获取评论操作
    public static String getPostInfo = "Index.getPostInfo";// 获取最新动态和公共建议
    public static String doTransfer = "Index.doTransfer";// 获取闲置转让列表
    public static String doPost = "Index.doPost";//发布最新动态 或 公共建议
    public static String getVoteInfo = "Index.getVoteInfo";// 获取投票列表
    public static String getVoteInfoById = "Index.getVoteInfoById";// 获取投票详情
    public static String doVote = "Index.doVote";//投票操作
    public static String getRoomListByFloor = "User.getRoomListByFloor";//  根据楼号获取户列表
    public static String getUserListByRoom = "User.getUserListByRoom";//  根据户获取用户列表
    public static String getAdminnotice = baseIp + "admin/webview/adminnotice_display/adminnotice_id/2";// 群组公告
    public static String getGuide = baseIp + "/admin/webview/guide_display/guide_id/4";// 装修指南
    public static String getConvention_display = baseIp + "admin/webview/convention_display";// 小区公约

}
