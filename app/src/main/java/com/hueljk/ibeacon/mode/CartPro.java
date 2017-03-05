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

public class CartPro {
    private int goodsid;
    private String des;
    private float price;
    private String url;
    private int mCheck;
    private String color;
    private String mAdd;
    private String mMinus;
    private String number;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    private Boolean isSelected ;

    public CartPro(int mId, String mName, float price, String icon, int check, String color, String add, String minus, String count) {
        goodsid = mId;
        des = mName;
        url = icon;
        price = price;
        mCheck = check;
        color = color;
        mAdd = add;
        mMinus = minus;
        number = count;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getmCheck() {
        return mCheck;
    }

    public void setmCheck(int mCheck) {
        this.mCheck = mCheck;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getmAdd() {
        return mAdd;
    }

    public void setmAdd(String mAdd) {
        this.mAdd = mAdd;
    }

    public String getmMinus() {
        return mMinus;
    }

    public void setmMinus(String mMinus) {
        this.mMinus = mMinus;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}




