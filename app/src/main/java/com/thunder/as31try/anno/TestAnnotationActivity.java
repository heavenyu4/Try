package com.thunder.as31try.anno;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.thunder.as31try.R;

public class TestAnnotationActivity extends AppCompatActivity {
    String TAG = "TestAnnotationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_annotation);


        new Thread(new Runnable() {
            @Override
            public void run() {
                printWorkInfo();
            }
        }).start();
    }

    @MainThread
    void printWorkInfo(){
        Log.d(TAG, "printWorkInfo: ");
    }
}
