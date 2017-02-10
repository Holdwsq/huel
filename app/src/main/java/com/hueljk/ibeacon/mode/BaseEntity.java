package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/1/19.
 */
public class BaseEntity {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("url")
    private String mUrl;

    public BaseEntity() {
    }

    public BaseEntity(int id, String name, String url) {
        mId = id;
        mName = name;
        mUrl = url;
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

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
