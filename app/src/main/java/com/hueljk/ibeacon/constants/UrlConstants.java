package com.hueljk.ibeacon.constants;

/**
 * Created by zc on 2017/1/19.
 */
public interface UrlConstants {
   // String baseUrl="http://192.168.191.1:8080/HuelJk";
   //String baseUrl="http://192.168.43.130:8080/HuelJk";
    //String baseUrl="http://192.168.43.1:8080/HuelJk";
    //http://192.168.191.1:8080/HuelJk/Home
    //169.254.180.107
   // String baseUrl="http://192.168.43.130:8080/HuelJk";
    String baseUrl="http://172.23.147.2:8080/HuelJk";
    String HomeUrl=baseUrl+"/Home";
    //二级页面clotingfragment中菜单栏的url
    String twoCloUrl=baseUrl+"/Clothing";
    //登录url
    String loginUrl = baseUrl+"/Login";
    //注册url
    String registerUrl=baseUrl+"/register";
    //二级搜索页面url
    String SearchUrl = baseUrl+"/Search";
    String picBaseUrl=baseUrl+"/picture/";//此处把‘/’加在路径之后，使用时不必再加
    //Home页面图片地址
    String HomePicUrl=picBaseUrl+"/goods/";
    //Home页面导航条图片的地址
    String BannerDisUrl=picBaseUrl+"image/";
    //http://192.168.8.239:8080/HuelJk/picture/image/
    //Home页面商品列表的商品图片地址
    String goodsUrl=picBaseUrl;
    //Home页面商品加入购物车发送给服务器的Url
    String addCartUrl=baseUrl+"/InsertCart";
    //二级页面clotingfragment中图片的地址
    String cloPicUrl = picBaseUrl + "goods/";
   //二级搜索页面图片地址
   String SearchPicUrl = picBaseUrl + "goods/";
   //三级页面商品详情页面url  http://localhost:8080/HuelJk/Detail
   //String DescUrl= baseUrl+"/Detail";
    String DescUrl=baseUrl+"/Detail";
 //三级页面商品详情页面url
   // http://localhost:8080/HuelJk/picture/image/detail/caomei.png
    String DescImgUrl=picBaseUrl+"image/";
    //详情页面视频地址   String videoUrl2 ="http://192.168.43.130:8080/HuelJk/video/HLA.mp4";
    String DescVideoUrl=baseUrl;
    //导购页面url
    String GuideUrl=baseUrl+"/IbeaconServlet";
    //导购页面图片url
    String guideImgUrl=picBaseUrl+"goods/";
    //修改购物车中的信息url
    String AlterCartUrl = baseUrl+"/AlterCart";
    //获取商品详情的url
    String detailMainUrl = baseUrl+"/DetailMain";
}
