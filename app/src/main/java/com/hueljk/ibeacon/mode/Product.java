package com.hueljk.ibeacon.mode;

import static android.R.attr.name;
import static android.support.v7.appcompat.R.attr.icon;

/**
 * 项目名称：HuelJk
 * 类描述:
 * 创建时间:2017/1/18 0018.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class Product extends BaseEntity {
    private float mPrice;
    private int mIcon;
    private int mCheck;
    private String mColor;
    private String mAdd;
    private String mMinus;
    private String mCount;

    public Product(String mId, String mName, String mUrl, float price, int icon, int check, String color, String add, String minus, String count) {
        super(mId, mName, mUrl);
        mPrice = price;
        mIcon = icon;
        mCheck = check;
        mColor = color;
        mAdd = add;
        mMinus = minus;
        mCount = count;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    public int getCheck() {
        return mCheck;
    }

    public void setCheck(int check) {
        mCheck = check;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getAdd() {
        return mAdd;
    }

    public void setAdd(String add) {
        mAdd = add;
    }

    public String getMinus() {
        return mMinus;
    }

    public void setMinus(String minus) {
        mMinus = minus;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }
}




