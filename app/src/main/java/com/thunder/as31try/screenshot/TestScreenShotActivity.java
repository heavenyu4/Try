package com.thunder.as31try.screenshot;

import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.thunder.as31try.R;

import java.util.Arrays;


public class TestScreenShotActivity extends AppCompatActivity {
    private String TAG = "TestScreenShotActivity";
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen_shot);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "onCreate: " + Build.MANUFACTURER
                    +"\n " + Build.BRAND
                    +"\n "+ Build.MODEL +"\n " + Build.PRODUCT  +"\n "
                    + Build.USER  +"\n "+ Arrays.toString(Build.SUPPORTED_ABIS) +"\n "+ Arrays.toString(Build.SUPPORTED_32_BIT_ABIS)
                    +"\n "+ Arrays.toString(Build.SUPPORTED_64_BIT_ABIS));
        }

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ScreenShotListenManager manager = ScreenShotListenManager.newInstance(getApplicationContext(), true);
//                manager.setListener(new ScreenShotListenManager.OnScreenShotListener() {
//                    @Override
//                    public void onShot(String imagePath) {
//                        Log.d(TAG, "onShot: " + imagePath);
//                    }
//                });
//                manager.startListen();

                mCountDownTimer.cancel();
            }
        });

//        mCountDownTimer = new CountDownTimer(30 * 1000, 5 * 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Log.d(TAG, "onTick: " + millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                Log.d(TAG, "onFinish: ");
//            }
//        };
//        mCountDownTimer.start();
    }
}