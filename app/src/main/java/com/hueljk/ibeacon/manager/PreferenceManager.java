package com.hueljk.ibeacon.manager;


import android.content.Context;
import android.content.SharedPreferences;

import com.hueljk.ibeacon.App;

/**
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
        editor = mSharedPreferences.edit();
        editor.putBoolean("isLoginSuccess", isLoginSuccess);
        editor.apply();
    }

    public boolean getLoginStatus() {
        return mSharedPreferences.getBoolean("isLoginSuccess", false);
    }

}
