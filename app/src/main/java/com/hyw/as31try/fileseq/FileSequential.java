package com.hyw.as31try.fileseq;

import android.util.Log;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: heaven
 * Time: 2019/6/18  15:20
 * Description:文件IO操作顺序执行测试
 */
public class FileSequential  {

//    private HandlerThread mThread;
//    private Handler mHandler;
//
//    private void init(){
//        mThread = new HandlerThread("file_io");
//        mThread.start();
//        mHandler = new Handler(mThread.getLooper(), this);
//    }
//
//    @Override
//    public boolean handleMessage(Message msg) {
//        return false;
//    }

    private ExecutorService mService;
    String TAG = "FileSequential";

    public void init(){
        //图片缩小处理的线程池
        mService = new ThreadPoolExecutor(1, 16,
                300L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new DefaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        DiskIoThreadExecutor.getIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: start copy dir");
                FileUtils.copy("/sdcard/xiaoai", "/storage/sda1/macfiles/xiaoai-dst", new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace(File srcFile, File destFile) {
                        return true;
                    }

                });
                Log.d(TAG, "run: end copy dir");
            }
        });

//        mService.execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "run: start copy dir");
//                FileUtils.copyDir("/sdcard/xiaoai", "/storage/sda1/macfiles/xiaoai-dst", new FileUtils.OnReplaceListener() {
//                    @Override
//                    public boolean onReplace() {
//                        return true;
//                    }
//                });
//                Log.d(TAG, "run: end copy dir");
//            }
//        });

        DiskIoThreadExecutor.getIOExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: start log.zip unzip");
                try {
                    ZipUtils.unzipFile("sdcard/log.zip", "/storage/sda1/macfiles/log_zip");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "run: end log.zip unzip ");
            }
        });
//        mService.execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "run: start log.zip unzip");
//                try {
//                    ZipUtils.unzipFile("sdcard/log.zip", "/storage/sda1/macfiles/log_zip");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.d(TAG, "run: end log.zip unzip ");
//            }
//        });
    }


}
