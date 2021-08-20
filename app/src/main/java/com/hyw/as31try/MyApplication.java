package com.hyw.as31try;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.hyw.as31try.service.LeakUploadService;
import com.lzy.okgo.OkGo;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    private final static MyApplication instance = new MyApplication();
    public RefWatcher refWatcher;

    private static String sCurProcessName = null;
    private String processName;
    private String packageName;

//       1.  Application的attachBaseContext方法是优先执行的；
//       2. ContentProvider的onCreate的方法 比 Application的onCreate的方法 先执行；
//       3. Activity、Service的onCreate方法以及BroadcastReceiver的onReceive方法，是在MainApplication的onCreate方法之后执行的；
//       4. 调用流程为： Application的attachBaseContext ---> ContentProvider的onCreate ---->
//       Application的onCreate ---> Activity、Service等的onCreate（Activity和Service不分先后）；


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        boolean isMainProcess = isMainProcess();
        if (isMainProcess) {

        }
    }

    private boolean isMainProcess() {
        processName = getCurProcessName(this);
        packageName = getPackageName();
        return !TextUtils.isEmpty(packageName) && TextUtils.equals(packageName, processName);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        多进程导致 Application 重复创建问题
        一个进程情况下，Application 的 onCreate 方法只会执行一次，但如果应用中采用多进程方式，onCreate 方法会执行多次。
         */
        boolean isMainProcess = isMainProcess();
        if (isMainProcess) {
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

    private static String getCurProcessName(Context context) {
        if (!TextUtils.isEmpty(sCurProcessName)) {
            return sCurProcessName;
        }
        sCurProcessName = getProcessName(android.os.Process.myPid());
        if (!TextUtils.isEmpty(sCurProcessName)) {
            return sCurProcessName;
        }
        try {
            int pid = android.os.Process.myPid();

            sCurProcessName = getProcessName(pid);
            if (!TextUtils.isEmpty(sCurProcessName)) {
                return sCurProcessName;
            }
            //获取系统的ActivityManager服务
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am == null) {
                return sCurProcessName;
            }
            for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    sCurProcessName = appProcess.processName;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sCurProcessName;
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }

            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


}
