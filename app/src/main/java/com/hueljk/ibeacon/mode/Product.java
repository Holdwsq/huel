package com.hueljk.ibeacon.mode;

/**
 * Created by zc on 2017/1/16.
 */
public class Product {
    private String mPurl;
    private String mPdec;
    private int mNum;
    private float mPrice;
    private int mCIon;

    public Product(String purl, String pdec, int num, float price, int CIon) {
        mPurl = purl;
        mPdec = pdec;
        mNum = num;
        mPrice = price;
        mCIon = CIon;
    }

    public String getPurl() {
        return mPurl;
    }

    public void setPurl(String purl) {
        mPurl = purl;
    }

    public String getPdec() {
        return mPdec;
    }

    public void setPdec(String pdec) {
        mPdec = pdec;
    }

    public int getNum() {
        return mNum;
    }

    public void setNum(int num) {
        mNum = num;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public int getCIon() {
        return mCIon;
    }

    public void setCIon(int CIon) {
        mCIon = CIon;
    }
}
