package com.hueljk.ibeacon;

import android.app.Application;
import android.content.Context;
import android.util.Log;


import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.IBle;
import com.brtbeacon.sdk.utils.L;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


/**
 * Created by Chuyh on 2017/2/9.
 */
public class App extends Application {
    private static App instance;
    private static final String TAG = App.class.getSimpleName();
    private BRTBeaconManager beaconManager;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
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
        initOkGo();

    }
    private void initOkGo() {
        // 构建OkHttpClient.builder
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 配置log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        // log打印级别，决定了log的显示的清晰程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        // log 颜色, 决定log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        /**
         * 设置超时时间 timeout 都是针对客户端的
         * readTimeout 等待服务端的响应数据 若服务端挂掉 不能一直傻等服务端
         * writeTimeout
         */
        // 全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        // 全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        // 全局链接时间 Http协议基于TCP/IP, TCP 连接需要三次握手协议
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        // header 不支持中文 和 特殊字符 这里设置为空
        HttpHeaders headers = null;
        // params 公共参数 支持中文
        HttpParams params = null;
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(3)
                .addCommonHeaders(headers)
                .addCommonParams(params);
    }
    public static App getInstance() {
        return instance;
    }
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
