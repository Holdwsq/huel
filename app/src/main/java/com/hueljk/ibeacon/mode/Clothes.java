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

    @SerializedName("icon")
    private String mCIonUrl;
    @SerializedName("CPrice")
    private float mCPrice;
    @SerializedName("cdec")
    private String mCdec;

    public Clothes(String CIon, float CPrice, String cdec) {
        mCIonUrl = CIon;
        mCPrice = CPrice;
        mCdec = cdec;
    }

    public String getCIon() {
        return mCIonUrl;
    }

    public void setCIon(String CIon) {
        mCIonUrl = CIon;
    }

    public double getCPrice() {
        return mCPrice;
    }

    public void setCPrice(float CPrice) {
        mCPrice = CPrice;
    }

    public String getCdec() {
        return mCdec;
    }

    public void setCdec(String cdec) {
        mCdec = cdec;
    }
}
