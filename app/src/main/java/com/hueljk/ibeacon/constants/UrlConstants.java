package com.hueljk.ibeacon.constants;

/**
 * Created by zc on 2017/1/19.
 */
public interface UrlConstants {
    String baseUrl="http://192.168.191.1:8080/HuelJk";
//    String baseUrl="http://192.168.43.130:8080/HuelJk";
    //String baseUrl="http://192.168.43.1:8080/HuelJk";
    //http://192.168.191.1:8080/HuelJk/Home
    String picBaseUrl=baseUrl+"/picture/";//此处把‘/’加在路径之后，使用时不必再加
    String goodsUrl=baseUrl+"/goods/";
    String clothing = baseUrl + "/clothing/";

}
