package com.hueljk.ibeacon.mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/2/13.
 */
public class User {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mUserName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("imageurl")
    private String mImageUrl;
    @SerializedName("telephone")
    private int mTelephone;
    @SerializedName("grade")
    private String mGrade;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("birthday")
    private String mBirthday;
    @SerializedName("integral")
    private String mIntegral;

    public User(int id, String userName, String password, String imageUrl, int telephone, String grade, String gender, String birthday, String integral) {
        mId = id;
        mUserName = userName;
        mPassword = password;
        mImageUrl = imageUrl;
        mTelephone = telephone;
        mGrade = grade;
        mGender = gender;
        mBirthday = birthday;
        mIntegral = integral;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public int getTelephone() {
        return mTelephone;
    }

    public void setTelephone(int telephone) {
        mTelephone = telephone;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        mGrade = grade;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getIntegral() {
        return mIntegral;
    }

    public void setIntegral(String integral) {
        mIntegral = integral;
    }
}
