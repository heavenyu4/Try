package com.thunder.as31try.pattern.chain;

/**
 * Author: heaven
 * Time: 2019/2/21  18:09
 * Description: 责任链模式测试
 * 责任链模式（Chain of Responsibility Pattern）为请求创建了一个接收者对象的链。
 * 这种模式给予请求的类型，对请求的发送者和接收者进行解耦。这种类型的设计模式属于行为型模式。
 在这种模式中，通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，
 那么它会把相同的请求传给下一个接收者，依此类推。
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
public class ResponsibilityChainTest {

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
