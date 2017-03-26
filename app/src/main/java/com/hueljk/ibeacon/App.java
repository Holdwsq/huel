package com.hueljk.ibeacon;

import android.app.Application;
import android.util.Log;


import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.IBle;
import com.brtbeacon.sdk.utils.L;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Chuyh on 2017/2/9.
 */
public class App extends Application {
    private static App instance;
    private static final String TAG = App.class.getSimpleName();
    private BRTBeaconManager beaconManager;

    @Override
    public void onCreate() {
        Log.d("Jpush", "[ExampleApplication] onCreate");
       // initSensoro();
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
        super.onCreate();
        instance = this;
        // 开启log打印
        L.enableDebugLogging(true);
        //获取单例
        beaconManager = BRTBeaconManager.getInstance(this);
        // 注册应用 APPKEY申请:http://brtbeacon.com/main/index.shtml
        beaconManager.registerApp("00000000000000000000000000000000");
        // 开启Beacon扫描服务
        beaconManager.startService();
    }

    public static App getInstance() {
        return instance;
    }

   /* private void initSensoro() {
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
    }*/
    /**
     * 创建Beacon连接需要传递此参数
     * @return IBle
     */
    public IBle getIBle() {
        return beaconManager.getIBle();
    }

    /**
     * 获取Beacon管理对象
     *
     * @return BRTBeaconManager
     */
    public BRTBeaconManager getBRTBeaconManager() {
        return beaconManager;
    }

}
