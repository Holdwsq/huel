package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zc on 2017/2/15.
 */
public class Desc {
    @SerializedName("mImage")
    private List<DescImg> mDescImgs;
    @SerializedName("mVideo")
    private Video mVideo;

    public Desc(Video video, List<DescImg> descImgs) {
        mVideo = video;
        mDescImgs = descImgs;
    }

    //用来控制列表数据的长度
    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public Desc() {
    }

    public List<DescImg> getDescImgs() {
        return mDescImgs;
    }

    public void setDescImgs(List<DescImg> descImgs) {
        mDescImgs = descImgs;
    }

    public Video getVideo() {
        return mVideo;
    }

    public void setVideo(Video video) {
        mVideo = video;
    }
}
