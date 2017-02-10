package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/2/9.
 */
public class Goods {
    /*
     "id": "4",
                "name": "草莓",
                "url": "Goods/strawberry.PNG",
                "price": 9.9,
                "desc": "水果草莓",
                "sold": 900
     */
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("url")
    private String mPurl;
    @SerializedName("price")
    private float mPrice;
    @SerializedName("desc")
    private String mPdesc;
    @SerializedName("sold")
    private int mSold;

    public Goods(int id, String name, String purl, float price, String pdesc, int sold) {
        mId = id;
        mName = name;
        mPurl = purl;
        mPrice = price;
        mPdesc = pdesc;
        mSold = sold;
    }

    public Goods() {
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

    public String getPurl() {
        return mPurl;
    }

    public void setPurl(String purl) {
        mPurl = purl;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public String getPdesc() {
        return mPdesc;
    }

    public void setPdesc(String pdesc) {
        mPdesc = pdesc;
    }

    public int getSold() {
        return mSold;
    }

    public void setSold(int sold) {
        mSold = sold;
    }
}
