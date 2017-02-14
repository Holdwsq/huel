package com.hueljk.ibeacon.constants;

/**
 * Created by zc on 2017/1/19.
 */
public interface UrlConstants {
   // String baseUrl="http://192.168.191.1:8080/HuelJk";
//    String baseUrl="http://192.168.43.130:8080/HuelJk";
    //String baseUrl="http://192.168.43.1:8080/HuelJk";
    //http://192.168.191.1:8080/HuelJk/Home
    //169.254.180.107
    //String baseUrl="http://192.168.8.239:8080/HuelJk";
    String baseUrl="http://192.168.10.40:8080/HuelJk";
    String HomeUrl=baseUrl+"/Home";
    String picBaseUrl=baseUrl+"/picture/";//此处把‘/’加在路径之后，使用时不必再加
    String BannerDisUrl=picBaseUrl+"image/";
 //http://192.168.8.239:8080/HuelJk/picture/image/
    String goodsUrl=picBaseUrl;
    String clothing = picBaseUrl + "/clothing/";

    //登录url
    String loginUrl = baseUrl+"/Login";

    //注册url
    String registerUrl=baseUrl+"/register";

}
