package com.hyw.as31try.fileseq;


import android.support.annotation.NonNull;
import android.util.Log;

import com.hyw.as31try.fileseq.base.BaseExecutor;
import com.hyw.as31try.fileseq.base.BaseExecutorProvider;
import com.hyw.as31try.fileseq.base.BaseWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhf on 16-11-3.
 */
class DiskIoThreadExecutor implements BaseExecutor {
    private static DiskIoThreadExecutor INSTANCE = new DiskIoThreadExecutor();
    String TAG = "DiskIoThreadExecutor";

    static DiskIoThreadExecutor getInstance() {
        return INSTANCE;
    }

    static BaseExecutor getIOExecutor() {
        return getInstance();
    }

//    private ExecutorService executorService;
    private ExecutorService singleExecutorService;

    private boolean forceExecuteOnNewThread = true;


    /**
     * 队列.
     */
    private final LinkedBlockingQueue<Runnable> mPoolWorkQueue = new LinkedBlockingQueue<Runnable>(Short.MAX_VALUE);

    /**
     * 保存线程数量 .
     */
    private static final int CORE_POOL_SIZE = 7;

    /**
     * 最大线程数量 .
     */
    private static final int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;

    /**
     * 活动线程数量 .
     */
    private static final long KEEP_ALIVE = 60L;

    private DiskIoThreadExecutor() {
        int numCores = 1;
        singleExecutorService = new ThreadPoolExecutor(
                numCores, 16,
                KEEP_ALIVE, TimeUnit.SECONDS, mPoolWorkQueue,
                new IoThreadPoolExecutorFactory("file-io"));
//        executorService = Executors.newCachedThreadPool(new IoThreadPoolExecutorFactory());

    }

    /**
     * nextStep execution:new io thread
     *
     * @return IoThreadExecutor
     */
    public BaseExecutor forceNewThread() {
        forceExecuteOnNewThread = true;
        return this;
    }

    private boolean isForceExecuteNewThread() {
        return forceExecuteOnNewThread;
    }


    @Override
    public int getActiveCount() {
//        if (executorService != null) {
//            return ((ThreadPoolExecutor) executorService).getActiveCount();
//        } else {
            return 0;
//        }
    }

    @NonNull
    @Override
    public BaseExecutorProvider execute(Runnable workRunnable) {
        return execute(workRunnable, 0);
    }

    @NonNull
    @Override
    public BaseExecutorProvider execute(Runnable workRunnable, long delayMilliTime) {

//        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()
//                || isForceExecuteNewThread()) {
        new IoWorker(singleExecutorService, workRunnable).execute(delayMilliTime);
//        } else {
//            workRunnable.run();
//        }

        return null;
    }

    @NonNull
    @Override
    public BaseExecutorProvider execute(Runnable workRunnable, long delayTime, TimeUnit unit) {
        return execute(workRunnable, unit.toMillis(delayTime));
    }

    @NonNull
    @Override
    public BaseExecutorProvider executeInQueue(Runnable workRunnable) {
        new IoWorker(singleExecutorService, workRunnable).execute(0);
        return null;
    }

    private class IoWorker implements BaseWorker {

        private final ExecutorService executorService;
        private Runnable workRunnable;

        IoWorker(@NonNull ExecutorService handler, @NonNull Runnable workRunnable) {
            this.executorService = handler;
            this.workRunnable = workRunnable;
        }

        @Override
        public boolean execute() {
            return execute(0);
        }

        @Override
        public boolean execute(long delayMillis) {
            executorService.submit(this);
            return true;
        }

        @Override
        public boolean cancel(Runnable runnable) {
            return false;
        }

        @Override
        public boolean cancel() {
            return false;
        }

        @Override
        public void run() {
            Log.d(TAG, "run: start");
            workRunnable.run();
        }

    }


}
