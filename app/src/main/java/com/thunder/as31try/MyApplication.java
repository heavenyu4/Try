package com.thunder.as31try;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.thunder.as31try.service.LeakUploadService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    private final static MyApplication instance = new MyApplication();
    public RefWatcher refWatcher;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        Utils.init(this);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setRetryCount(0);


        refWatcher = LeakCanary.refWatcher(this)
                .listenerServiceClass(LeakUploadService.class)
                .buildAndInstall();
        refWatcher.watch(this);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this, "");
    }


}
