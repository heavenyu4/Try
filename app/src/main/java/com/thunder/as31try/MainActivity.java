package com.thunder.as31try;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.thunder.as31try.activity.LeakTestActivity;
import com.thunder.as31try.bean.MiTokenInfo;
import com.thunder.as31try.utils.FileIOUtilsThird;
import com.thunder.as31try.utils.Logger;
import com.thunder.as31try.utils.WeakHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends BaseActivity {

    private TextView tv;

    public static void main(String[] arg) {
        String in1 = "/video";
        if (in1.startsWith("/video")) {
            System.out.println("dd");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    private void initView() {
        tv = ((TextView) findViewById(R.id.tv));
    }

    private void initData() {

        testAnJianYin();

//        testFastJson();

//        testAnnAge();

////        tv.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//////                finish();
//////                jumpToLeak();
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        Instrumentation inst = new Instrumentation();
////                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
////                    }
////                }).start();
//
//            }
//        });

        //test leaknary
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                //另一个线程中持有Activity的引用，并且不释放
//                while (true) ;
//            }
//        }.execute();
//        testUri();
//        testTouch();

//        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        Logger.error(TAG, "initData: absolutePath: " + absolutePath);
//        String packageName = getPackageName();
//        Logger.error(TAG, "initData: packageName: "+packageName);

        //视频
//        Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        innerIntent.setType("video/*"); //String VIDEO_UNSPECIFIED = "video/*";
//        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
//        startActivityForResult(wrapperIntent, 101);

//        testHandler();

//        testDefaultHandler();

//        testAtomic();

    }

    private void testAnJianYin() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        manager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    private void testFastJson() {
//        String json = "{\"expireLiveTime\":604800,\"refreshToken\":\"TOyr2UlYDQU.We6-2VxaG4XcbF9IjbS2AlT0k6AdbnxcPouA4C0WfWMn2p-LCj46VHHRs_EfH2U0LoH5tSo0X2kv3_qgBQxMcgOB_q4GMmVoA4bqoAY1R2gDhsezX4bK0PrnvdlsWA0.nHSk-LhsEF_5tuxMuPnxYNX062-BIigxf4OyWSWB_S8\",\"token\":\"odwWRorlW-s.tNwDRp_nTWsxXOLXTgng7LnELj_e0yqy07s9f-6rK43K6iIUyYNsupzhDm7cojPaw7FEKumJCYfC70c_xrEanO6xQzHFjzOG7rZXP8WIEYbutnosnDucPhfXAEav5Vs.C80_Cn8_4EihjuQaO1ySXkLPNVXH6mmbOBAVcDy-VSQ\",\"tokenStartTime\":1533719023343}";

        String token_filename = Environment.getExternalStorageDirectory() + "/mitoken.json";
        String jsonString = FileIOUtilsThird.readFile2String(token_filename);

        MiTokenInfo info = JSON.parseObject(jsonString, MiTokenInfo.class);
        Log.d(TAG, "testFastJson: jsonString:　" + jsonString);
        Log.d(TAG, "testFastJson: info:     " + info);

        JSONObject object = null;
        try {
            object = new JSONObject(jsonString);

            MiTokenInfo info1 = new MiTokenInfo();
            info1.token = object.getString("token");
            info1.refreshToken = object.getString("refreshToken");
            info1.expireLiveTime = object.getInt("expireLiveTime");
            info1.tokenStartTime = object.getLong("tokenStartTime");
            Log.d(TAG, "testFastJson: info1:     " + info1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void testAtomic() {
        AtomicBoolean stop = new AtomicBoolean();
    }

    @AnnoAddr(name = "test")
    String nameTest = "empty";

    private void testAnnotation() {
        Log.d(TAG, "testAnnotation: nameTest: " + nameTest);
        Field[] declaredFields = getClass().getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (int i = 0; i < declaredFields.length; i++) {
                Log.d(TAG, "testAnnotation: " + declaredFields[i].getName());
                AnnoAddr annAddr = declaredFields[i].getAnnotation(AnnoAddr.class);
                if (annAddr != null) {
                    Log.d(TAG, "testAnnotation: dec" + declaredFields[i].getName() + " : " + annAddr.name());
                }
            }
        }
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface AnnoAddr {
        String name();
    }

    @AnnoAge(age = "demo")
    private void testAnnAge() {
        Log.d(TAG, "testAnnAge: ");
        Method[] declaredMethods = getClass().getDeclaredMethods();
        if (declaredMethods != null && declaredMethods.length > 0) {
            for (int i = 0; i < declaredMethods.length; i++) {
                AnnoAge annoAge = declaredMethods[i].getAnnotation(AnnoAge.class);
                if (annoAge != null) {
                    Log.d(TAG, "testAnnAge: " + annoAge.age());
                    int[] size = annoAge.size();
                    if (size != null && size.length > 0) {
                        Log.d(TAG, "testAnnAge: " + size);
                    }
                }
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AnnoAge {
        String age();

        int[] size() default {4, 5};
    }

    private void jumpToLeak() {
        Intent intent = new Intent(this, LeakTestActivity.class);
        startActivity(intent);
        finish();
    }

    private void testTouch() {
        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        fl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Logger.debug(TAG, "onTouch: x: " + motionEvent.getRawX() + " y: " + motionEvent.getRawY());
                return true;
            }
        });
    }

    private void testUri() {
        String addr = "http://10.0.0.157/skyrocker_android_aging/factory.json";
        try {
            URL url = new URL(addr);
            Logger.debug(TAG, "testUri: ");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void testDefaultHandler() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG, "handleMessage: default handler");
                Toast.makeText(MainActivity.this, "who am I ori", Toast.LENGTH_SHORT).show();

            }
        };

        handler.sendEmptyMessageDelayed(0, 1000 * 60);
    }

    private void testHandler() {
//        WeakHandler handler = new WeakHandler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(MainActivity.this, "who am I", Toast.LENGTH_SHORT).show();
//            }
//        }, 1000*60);
//
        WeakHandler handler = new WeakHandler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                Log.d(TAG, "handleMessage: weakhandler deal something");
                Toast.makeText(MainActivity.this, "=====================================\n" +
                        "dddddddddddddddddddddddd\n==============================\n", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        handler.sendEmptyMessageDelayed(0, 1000 * 15);
        handler.sendEmptyMessageDelayed(0, 1000 * 3);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && data != null) {
            Logger.error(TAG, "onActivityResult: ");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
