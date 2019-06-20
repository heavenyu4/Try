package com.thunder.as31try.fileseq;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by valentinorossi on 2017/3/6.
 */

public class IoThreadPoolExecutorFactory implements ThreadFactory {
    private final static AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    IoThreadPoolExecutorFactory() {

        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "IoE-" +
                poolNumber.getAndIncrement() +
                "-t-";

    }

    IoThreadPoolExecutorFactory(String name) {

        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = name + "-IoE-" +
                poolNumber.getAndIncrement() +
                "-t-";

    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }

        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }


}
