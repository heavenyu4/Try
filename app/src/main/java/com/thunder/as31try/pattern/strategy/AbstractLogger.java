package com.thunder.as31try.pattern.strategy;

/**
 * Author: heaven
 * Time: 2019/2/21  17:21
 * Description:
 */
public abstract class AbstractLogger {

    //log优先级 值越低优先级越高
    public static int DEBUG = 1;
    public static int INFO = 2;
    public static int ERR = 3;

    int level;
    AbstractLogger nextLogger;

    void setNextLogger(AbstractLogger logger) {
        if (logger != null) {
            nextLogger = logger;
        }
    }

    AbstractLogger(int level) {
        this.level = level;
    }

    public void pringLog(int level, String msg) {
        if (level <= this.level) {
            showLog(msg);
        }
        if (nextLogger != null) {
            nextLogger.pringLog(level, msg);
        }
    }

    abstract void showLog(String msg);
}
