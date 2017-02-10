package com.hueljk.ibeacon;

import android.app.Application;

/**
 * Created by Chuyh on 2017/2/9.
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance(){
        return instance;
    }
}
