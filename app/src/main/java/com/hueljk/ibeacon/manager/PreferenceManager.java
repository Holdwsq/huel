package com.hueljk.ibeacon.manager;


import android.content.Context;
import android.content.SharedPreferences;

import com.hueljk.ibeacon.App;

/**
 * 共享文件是用来存储
 * 状态信息：是否登录、是否记住账号密码
 * 临时信息：当前的userid、头像url、其他的基本信息
 * 配置信息：版本号
 *
 * @author Chuyh 创建于：2016-8-2
 */
public class PreferenceManager {
    /**
     * 保存Preference的name
     */
    public static final String TEMP = "yuntiaowei";

    private static SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferenceManager;
    private static SharedPreferences.Editor editor;

    private PreferenceManager(String userName) {
        mSharedPreferences = App.getInstance().getSharedPreferences(userName,
                Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        editor.apply();
    }

    /**
     * 单例模式，获取instance实例
     *
     * @return
     */
    public synchronized static PreferenceManager getInstance() {
        if (mPreferenceManager == null) {
            mPreferenceManager = new PreferenceManager(TEMP);
        }
        return mPreferenceManager;
    }

    public void saveLoginStatus(boolean isLoginSuccess) {
        //存储数据的三个步骤

        editor.putBoolean("isLoginSuccess", isLoginSuccess);
        editor.commit();
    }
    public boolean getLoginStatus() {
        return mSharedPreferences.getBoolean("isLoginSuccess", false);
    }
    public void saveUserId(int userid){
        editor.putInt("UserId",userid);
        editor.commit();
    }
    public void saveSessionId(String sessionId){
        editor.putString("SessionId", sessionId);
        editor.commit();
    }
    public int getUserId(){
        return mSharedPreferences.getInt("UserId",0);
    }
    public void saveUserName(String userName){
        editor.putString("UserName",userName);
        editor.commit();
    }
    public String getUserName(){
        return mSharedPreferences.getString("UserName","未登录");
    }



}
