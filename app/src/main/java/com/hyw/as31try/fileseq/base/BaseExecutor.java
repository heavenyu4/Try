package com.hyw.as31try.fileseq.base;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * Created by lhf on 16-11-3.
 */

public interface BaseExecutor {

    /**
     * 当前执行Executor的正在执行的线程数
     *
     * @return count
     */
    int getActiveCount();

    @NonNull
    BaseExecutorProvider execute(Runnable runnable);

    @NonNull
    BaseExecutorProvider execute(Runnable runnable, long delayMilliTime);

    @NonNull
    BaseExecutorProvider execute(Runnable runnable, long delayTime, TimeUnit unit);

    @NonNull
    BaseExecutorProvider executeInQueue(Runnable runnable);

}
