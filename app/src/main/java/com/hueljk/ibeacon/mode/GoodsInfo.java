package com.hueljk.ibeacon.mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/17.
 */

public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = -1390799328747552550L;
    /**
     * 商品id
     */
    private String id;
    /**
     * 发布人id
     */
    private String userId;
    /**
     * 发布商品人姓名
     */
    private String userName;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     *  发布人头像
     */
    private String userPortrait;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品价格
     */
    private Float price;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 文件请求路径集合
     */
    private List<String> fileUrls;
    /**
     * 商品类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(List<String> fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
