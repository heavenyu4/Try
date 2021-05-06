package com.hyw.as31try.testMultiThread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

/**
 * Author: heaven
 * Time: 2019/10/14  21:10
 * Description: 测试3个线程, A线程初始化一个变量, B,C线程要获取这个初始化的结果
 * 这里利用信号量实现
 */
public class TestHandler {


    static int num = 0;

    public static void main(String[] args) {
        HandlerThread handlerThread = new HandlerThread("A");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        num = 5;
                        sendEmptyMessageDelayed(1, 2_000);
                        sendEmptyMessageDelayed(2, 2_000);
                        break;
                    case 1:
                        System.out.println(num);
                        break;
                    case 2:
                        System.out.println(num);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + msg.what);
                }
            }
        };

        handler.sendEmptyMessage(0);

    }

}
