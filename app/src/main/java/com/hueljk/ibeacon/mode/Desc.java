package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zc on 2017/2/15.
 */
public class Desc {
    @SerializedName("mImage")
    private List<DescImg> mDescImgs;
    @SerializedName("mGoods")
    private DescPrameter mDescPrameters;

    //用来控制列表数据的长度
    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Desc(List<DescImg> descImgs, DescPrameter descPrameters) {
        mDescImgs = descImgs;
        mDescPrameters = descPrameters;
    }

    public Desc() {
    }

    public List<DescImg> getDescImgs() {
        return mDescImgs;
    }

    public void setDescImgs(List<DescImg> descImgs) {
        mDescImgs = descImgs;
    }

    public DescPrameter getDescPrameters() {
        return mDescPrameters;
    }

    public void setDescPrameters(DescPrameter descPrameters) {
        mDescPrameters = descPrameters;
    }
}
