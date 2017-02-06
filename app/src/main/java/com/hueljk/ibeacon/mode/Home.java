package com.hueljk.ibeacon.mode;

import java.util.List;

/**
 * Created by zc on 2017/1/20.
 */
public class Home {
    private List<BaseEntity> mBanners;
    private List<BaseEntity> mMains;
    private List<BaseEntity> mDiscounts;
    private List<Product> mProducts;

    public List<BaseEntity> getBanners() {
        return mBanners;
    }

    public void setBanners(List<BaseEntity> banners) {
        mBanners = banners;
    }

    public List<BaseEntity> getMains() {
        return mMains;
    }

    public void setMains(List<BaseEntity> mains) {
        mMains = mains;
    }

    public List<BaseEntity> getDiscounts() {
        return mDiscounts;
    }

    public void setDiscounts(List<BaseEntity> discounts) {
        mDiscounts = discounts;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }
}
