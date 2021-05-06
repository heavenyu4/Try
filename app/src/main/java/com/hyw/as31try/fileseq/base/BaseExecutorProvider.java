package com.hyw.as31try.fileseq.base;

import android.support.annotation.NonNull;

/**
 * Created by lhf on 16-11-3.
 */

public interface BaseExecutorProvider {
    @NonNull
    BaseExecutor io();

    @NonNull
    BaseExecutor serializeIo();

    /**
     * do nothing for now
     *
     * @return an Executor
     * @NonNull BaseExecutor newIo();
     */


    @NonNull
    BaseExecutor ui();

    @NonNull
    BaseExecutor disk();

}
