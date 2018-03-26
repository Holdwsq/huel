package com.hueljk.ibeacon.mode;

import java.util.List;

/**
 * Home 信息
 * Created by wsq on 2017/1/20.
 */
public class Home {
    private List<BaseEntity> homeBanners;
    private List<BaseEntity> homeDiscounts;
    private PageData<GoodsInfo> goodsInfos;

    public Home(List<BaseEntity> banners, List<BaseEntity> discounts, PageData<GoodsInfo> goodsInfos) {
        super();
        homeBanners = banners;
        homeDiscounts = discounts;
        goodsInfos = goodsInfos;
    }
    public Home(){
        super();
    }

    public List<BaseEntity> getHomeBanners() {
        return homeBanners;
    }

    public void setHomeBanners(List<BaseEntity> homeBanners) {
        this.homeBanners = homeBanners;
    }

    public List<BaseEntity> getHomeDiscounts() {
        return homeDiscounts;
    }

    public void setHomeDiscounts(List<BaseEntity> homeDiscounts) {
        this.homeDiscounts = homeDiscounts;
    }

    public PageData<GoodsInfo> getGoodsInfos() {
        return goodsInfos;
    }

    public void setGoodsInfos(PageData<GoodsInfo> goodsInfos) {
        this.goodsInfos = goodsInfos;
    }
}
