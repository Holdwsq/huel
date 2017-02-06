package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/1/19.
 */
public class Result<T> {
    @SerializedName("code")
    public int mCode;
    @SerializedName("msg")
    public String mMsg;
    @SerializedName("data")
    public T mData;

}
