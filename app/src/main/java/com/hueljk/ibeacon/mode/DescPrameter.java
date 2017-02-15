package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by zc on 2017/2/15.
 */
public class DescPrameter {
    /*"id": 1,
            "timetamp": "2017-02-10 10:12:02.0",
            "size": "175",
            "color": "灰色",
            "shelf": 0,
            "stock": 103*/
    @SerializedName("id")
    private int mId;
    @SerializedName("timetamp")
    private String mTimetamp;
    @SerializedName("size")
    private String mSize;
    @SerializedName("color")
    private String mColor;
    @SerializedName("shelf")
    private int mShelf;
    @SerializedName("stock")
    private int mStock;

    public DescPrameter(int id, String timetamp, String size, String color, int shelf, int stock) {
        mId = id;
        mTimetamp = timetamp;
        mSize = size;
        mColor = color;
        mShelf = shelf;
        mStock = stock;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTimetamp() {
        return mTimetamp;
    }

    public void setTimetamp(String timetamp) {
        mTimetamp = timetamp;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public int getShelf() {
        return mShelf;
    }

    public void setShelf(int shelf) {
        mShelf = shelf;
    }

    public int getStock() {
        return mStock;
    }

    public void setStock(int stock) {
        mStock = stock;
    }
}
