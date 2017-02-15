package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/1/17.
 */
/*
 "mData": [{
  "mID": "12",
  "mName": "hui",
  "mUrl": "hxidh"
 },
 {
  "mID": "14",
  "mName": "ling",
  "mUrl": "dfgf"
 },
 {
  "mID": "16",
  "mName": "fgc",
  "mUrl": "gvd"
 }]
 */
public class Clothes {
    /*"id": 9,
            "name": "套装冬卫衣",
            "url": "clothing/rqrong.png",
            "price": 125.0,
            "desc": "金丝绒套装女加厚天鹅绒套装冬卫衣时尚2017两件套"*/
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("url")
    private String mCloUrl;
    @SerializedName("price")
    private float mCloPrice;
    @SerializedName("des")
    private String mCloDesc;

    public Clothes(int id, String name, String cloUrl, float cloPrice, String cloDesc) {
        mId = id;
        mName = name;
        mCloUrl = cloUrl;
        mCloPrice = cloPrice;
        mCloDesc = cloDesc;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCloUrl() {
        return mCloUrl;
    }

    public void setCloUrl(String cloUrl) {
        mCloUrl = cloUrl;
    }

    public float getCloPrice() {
        return mCloPrice;
    }

    public void setCloPrice(float cloPrice) {
        mCloPrice = cloPrice;
    }

    public String getCloDesc() {
        return mCloDesc;
    }

    public void setCloDesc(String cloDesc) {
        mCloDesc = cloDesc;
    }
}
