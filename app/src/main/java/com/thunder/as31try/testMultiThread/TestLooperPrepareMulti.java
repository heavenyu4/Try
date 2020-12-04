package com.thunder.as31try.testMultiThread;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.thunder.as31try.R;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试子线程多次调用Looper.prepare的后果
 */
public class TestLooperPrepareMulti extends AppCompatActivity {
    private String TAG = "TestLooperPrepareMulti";
    private ThreadPoolExecutor mThreadPoolExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_looper_prepare_multi);

        initThread();
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            Looper.prepare();
            Log.d(TAG, "run: " + Thread.currentThread());
//            Looper.loop();
        }
    };

    private void initThread() {
        mThreadPoolExecutor = new ThreadPoolExecutor(0, 4, 60,
                TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < 10; i++) {
//            mThreadPoolExecutor.execute(new ThreadRunnable(new ThreadCallback() {
//                @Override
//                public void onResult(String result) {
//                    Looper.prepare();
//                    Log.d(TAG, "run: " + Thread.currentThread() + " result: " + result);
//                    Looper.loop();
//                }
//            }));
            mThreadPoolExecutor.execute(mRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThreadPoolExecutor.shutdownNow();
    }

    interface ThreadCallback {
        void onResult(String result);
    }

    class ThreadRunnable implements Runnable {
        ThreadCallback callback;

        public ThreadRunnable(ThreadCallback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            if (callback != null){
                callback.onResult(Thread.currentThread().getName());
            }
        }
    }
}