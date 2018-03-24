package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 2018/3/16.
 */

public class ResponseBean<T> implements Serializable{
    private static final long serialVersionUID = -7417105932300361632L;
    /**
     * status 响应状态码 0 失败 1 成功
     */
    public static final String STATUS_ERROR = "0";
    public static final String STATUS_SUCCESS = "1";
    /**
     * 响应状态 0 - 失败 1 - 成功
     */
    private String status;
    /**
     * 响应消息 message
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
