package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zc on 2017/1/20.
 */
public class Home {
    @SerializedName("banner")
    private List<BaseEntity> mBanners;
    @SerializedName("discount")
    private List<BaseEntity> mDiscounts;
    @SerializedName("goods")
    private List<Goods> mGoods;

    public Home(List<BaseEntity> banners, List<BaseEntity> discounts, List<Goods> goods) {

        mBanners = banners;
        mDiscounts = discounts;
        mGoods = goods;
    }

    public Home() {
    }

    public List<BaseEntity> getBanners() {
        return mBanners;
    }

    public void setBanners(List<BaseEntity> banners) {
        mBanners = banners;
    }

    public List<BaseEntity> getDiscounts() {
        return mDiscounts;
    }

    public void setDiscounts(List<BaseEntity> discounts) {
        mDiscounts = discounts;
    }

    public List<Goods> getGoods() {
        return mGoods;
    }

    public void setGoods(List<Goods> goods) {
        mGoods = goods;
    }
}
