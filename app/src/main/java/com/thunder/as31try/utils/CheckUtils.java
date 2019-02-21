package com.thunder.as31try.utils;

import android.os.Looper;

/**
 * Created by zhangshichuan on 2017/3/1.
 */

public class CheckUtils {
    private CheckUtils() {
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }


    private static final long MAIN_THREAD_ID = Looper.getMainLooper().getThread().getId();

    public static boolean isMainThread() {
        return Thread.currentThread().getId() == MAIN_THREAD_ID;
    }

    @SuppressWarnings("WeakerAccess")
    public static void checkMainThread() {
        if (!isMainThread()) {
            throw new RuntimeException("must be called on UI Thread");
        }
    }
}
