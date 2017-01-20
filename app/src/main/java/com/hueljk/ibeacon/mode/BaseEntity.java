package com.hueljk.ibeacon.mode;

/**
 * 项目名称：HuelJk
 * 类描述:
 * 创建时间:2017/1/18 0018.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 * 所有实体类的根类。
 */

public class BaseEntity {
    private String mId;
    private String mName;
    private String mUrl;

    public BaseEntity() {
    }

    public BaseEntity(String mId, String mName, String mUrl) {
        this.mId = mId;
        this.mName = mName;
        this.mUrl = mUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
