package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/2/15.
 */
public class DescImg {
    /*"goodsid": 1,
            "id": 13,
            "url": "detail/fengyi3.png"*/
    @SerializedName("goodsid")
    private int mGoodsId;
    @SerializedName("id")
    private int mId;
    @SerializedName("url")
    private String url;

    public DescImg(int goodsId, int id, String url) {
        mGoodsId = goodsId;
        mId = id;
        this.url = url;
    }

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
