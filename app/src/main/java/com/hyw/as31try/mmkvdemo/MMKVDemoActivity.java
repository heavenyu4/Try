package com.hyw.as31try.mmkvdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.mmkv.MMKV;
import com.hyw.as31try.R;

/***
 * MMKV测试性能，与sp相比
 */
public class MMKVDemoActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkvdemo);
        String rootPath = MMKV.initialize(this);
        Log.d(TAG, "onCreate: " + rootPath);
//        testSP();
//        testMMKV();
        testMMKVUse();

    }

    private void testMMKVUse() {
        MMKV mmkv = MMKV.defaultMMKV();
        mmkv.putInt("test", 9837362);

        int result = mmkv.getInt("test", 0);
        Log.d(TAG, "testMMKVUse: " + result);
    }

    /**
     * 测试用AS自带的模拟器
     * 耗时3006ms
     */
    private void testSP(){

        long startTime = SystemClock.elapsedRealtime();
        SharedPreferences sp = getSharedPreferences("mmkv_sp_compare", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        for (int i = 0; i < 1000; i++) {
            edit.putInt("ind"+i, i).apply();
        }
        long internal = SystemClock.elapsedRealtime() - startTime;
        Log.d(TAG, "testSP: " + internal);

    }

    /**
     * 测试mmkv的性能
     * 耗时10ms
     */
    private void testMMKV(){
        long startTime = SystemClock.elapsedRealtime();
        MMKV mmkv = MMKV.defaultMMKV();
        for (int i = 0; i < 1000; i++) {
//            mmkv.encode("mmkv"+i, i);
            mmkv.putInt("mmkv" + i, i);
        }
        long internal = SystemClock.elapsedRealtime() - startTime;
        Log.d(TAG, "testMMKV: " + internal);

    }
}
