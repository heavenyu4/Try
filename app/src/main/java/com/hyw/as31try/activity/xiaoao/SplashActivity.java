package com.hyw.as31try.activity.xiaoao;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hyw.as31try.R;

import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends Activity {
    private String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, LogoTestActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivity(intent);
//                finish();
//            }
//        }, 1_000);

        //设置全屏
//        Window window = getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        WindowManager.LayoutParams lp = null;
//        lp = window.getAttributes();
//        //设置隐藏虚拟按钮
//        lp.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
//        window.setAttributes(lp);
//
//        sendClientStartup("", "");

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        Log.d(TAG, "onCreate: screenWidth: " + screenWidth + " screenHeight: " + screenHeight);

        DisplayMetrics dm1 = getResources().getDisplayMetrics();
        int screenWidth1 = dm1.widthPixels;
        int screenHeight1 = dm1.heightPixels;
        Log.d(TAG, "onCreate: screenWidth1: " + screenWidth1 + " screenHeight1: " + screenHeight1);

    }


    private void sendClientStartup(String version, String uuid) {
        final String fVersion = version;
        final String fUUID = uuid;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int retry = 3;
                while (retry >= 0) {
                    retry--;
                    try {
                        HttpURLConnection connection = (HttpURLConnection) new URL("http://www.baidu.com").openConnection();
                        connection.setConnectTimeout(2000);
                        connection.setRequestMethod("GET");
                        int responseCode = connection.getResponseCode();
                        Log.d("Unity", "Send GameClientLogin Resp " + responseCode);
                        if (responseCode == 200) {
                            return;
                        }
                    } catch (Exception e) {
                        Log.e("Unity", "Send GameClientLogin Failed");
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}