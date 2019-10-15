package com.thunder.as31try.testMultiThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.thunder.as31try.R;

public class TestHandlerActivity extends AppCompatActivity {

     int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler);

        HandlerThread handlerThread = new HandlerThread("A");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        num = 5;
                        System.out.println("init " + num);
                        sendEmptyMessageDelayed(1, 2_000);
                        sendEmptyMessageDelayed(2, 2_000);
                        break;
                    case 1:
//                        System.out.println("b1:" + num);
//                        num++;
                        SystemClock.sleep(2_000);
                        System.out.println("b2:" + num);
                        break;
                    case 2:
                        System.out.println("c:" + num);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + msg.what);
                }
            }
        };

        handler.sendEmptyMessage(0);
    }
}
