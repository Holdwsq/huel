package com.hueljk.ibeacon.mode;

/**
 * 通用实体
 * Created by wsq on 2017/1/19.
 */
public class BaseEntity {
    /**
     * 通用id
     */
    private int id;
    /**
     * 实体名称
     */
    private String name;
    /**
     * 实体路径
     */
    private String url;

    public BaseEntity() {
    }

    public BaseEntity(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
