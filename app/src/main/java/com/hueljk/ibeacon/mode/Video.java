package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/3/19.
 */
public class Video {
    @SerializedName("id")
    private int mVid;
    @SerializedName("mVideo")
    private String mVideoUrl;

    public int getVid() {
        return mVid;
    }

    public void setVid(int vid) {
        mVid = vid;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public Video(int vid, String videoUrl) {

        mVid = vid;
        mVideoUrl = videoUrl;
    }
}
