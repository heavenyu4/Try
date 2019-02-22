package com.thunder.as31try.pattern.chain;

/**
 * Author: heaven
 * Time: 2019/2/21  17:37
 * Description:
 */
public class InfoLogger extends AbstractLogger {
    public InfoLogger(int level) {
        super(level);
    }

    @Override
    void showLog(String msg) {
//        Logger.info(msg);
        System.out.println("Info: " + msg);
    }
}
