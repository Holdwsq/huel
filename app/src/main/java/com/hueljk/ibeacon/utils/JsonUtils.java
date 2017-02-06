package com.hueljk.ibeacon.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by LongWH on 2016/9/26.
 * All Rights Reserved by ZhiYou @2016 - 2017
 */
public class JsonUtils {
    private static Gson mGson = new Gson();

    /**把一个Object类型的数据转换为 Json格式的字符串
     * @param object ：
     * @return ：
     */
    public static String toJson(Object object) {
        return mGson.toJson(object);
    }

    public static <T> T parse(String json,Type type) {
        return mGson.fromJson(json,type);
    }
}
