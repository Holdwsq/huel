package com.hueljk.ibeacon;

import android.app.Application;
import android.util.Log;

import com.sensoro.cloud.SensoroManager;


/**
 * Created by Chuyh on 2017/2/9.
 */
public class App extends Application {
    private static App instance;
    private static final String TAG = App.class.getSimpleName();

    public SensoroManager sensoroManager;

    @Override
    public void onCreate() {
        initSensoro();
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    private void initSensoro() {
        sensoroManager = SensoroManager.getInstance(getApplicationContext());
        sensoroManager.setCloudServiceEnable(false);
//        sensoroManager.addBroadcastKey("01Y2GLh1yw3+6Aq0RsnOQ8xNvXTnDUTTLE937Yedd/DnlcV0ixCWo7JQ+VEWRSya80yea6u5aWgnW1ACjKNzFnig==");
        try {
            sensoroManager.startService();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onTerminate() {
        if (sensoroManager != null) {
            sensoroManager.stopService();
        }
        super.onTerminate();
    }


}
