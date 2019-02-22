package com.thunder.as31try.pattern.strategy;

/**
 * Author: heaven
 * Time: 2019/2/21  18:08
 * Description:
 */
public class ErrLogger extends AbstractLogger {
    public ErrLogger(int level) {
        super(level);
    }

    @Override
    void showLog(String msg) {
        System.out.println("err: " + msg);
    }
}
