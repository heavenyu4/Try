package com.thunder.as31try.pattern.strategy;

/**
 * Author: heaven
 * Time: 2019/2/21  18:09
 * Description: 责任链模式测试
 *
 *
 * 发的3种消息, err info debug 按照level等级,
 * 责任链 err->info->debug
 * err就只在一级处理了
 * err: msg test for err

 info 二级处理, 这个可以在err和info里获取到
 err: msg test for info
 Info: msg test for info

 debug 三级处理, 这个可以在err info debug里获取到
 err: msg test for debug
 Info: msg test for debug
 debug: msg test for debug
 */
public class StrategeyTest {

    public static void main(String[] args) {
        InfoLogger infoLogger = new InfoLogger(AbstractLogger.INFO);
        DebugLogger debugLogger = new DebugLogger(AbstractLogger.DEBUG);
        ErrLogger errLogger = new ErrLogger(AbstractLogger.ERR);

        errLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(debugLogger);

        errLogger.pringLog(AbstractLogger.ERR, "msg test for err");
        System.out.println();
        errLogger.pringLog(AbstractLogger.INFO, "msg test for info");
        System.out.println();
        errLogger.pringLog(AbstractLogger.DEBUG, "msg test for debug");
    }
}
