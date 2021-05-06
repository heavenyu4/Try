package com.hyw.as31try.fileseq.base;

/**
 * Created by lhf on 16-11-3.
 */

public interface BaseWorker extends Runnable {

    boolean execute();
    boolean execute(long delayMillis);
    boolean cancel(Runnable runnable);
    boolean cancel();

}
