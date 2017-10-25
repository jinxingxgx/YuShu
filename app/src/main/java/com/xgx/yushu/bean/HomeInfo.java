package com.xgx.yushu.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xgx on 2017/9/17 0017 for YuShu
 */

public class HomeInfo {
    private List<BroadcastInfo> broadcastlist;
    private List<SlideInfo> slidelist;
    private List<ImageInfo> imageList;
    private List<VideoInfo> videoList;

    public List<BroadcastInfo> getBroadcastlist() {
        return broadcastlist == null ? new ArrayList<BroadcastInfo>() : broadcastlist;
    }

    public void setBroadcastlist(List<BroadcastInfo> broadcastlist) {
        this.broadcastlist = broadcastlist;
    }

    public List<SlideInfo> getSlidelist() {
        return slidelist == null ? new ArrayList<SlideInfo>() : slidelist;
    }

    public void setSlidelist(List<SlideInfo> slidelist) {
        this.slidelist = slidelist;
    }

    public List<ImageInfo> getImageList() {
        return imageList == null ? new ArrayList<ImageInfo>() : imageList;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }

    public List<VideoInfo> getVideoList() {
        return videoList == null ? new ArrayList<VideoInfo>() : videoList;
    }

    public void setVideoList(List<VideoInfo> videoList) {
        this.videoList = videoList;
    }
}
