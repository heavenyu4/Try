package com.thunder.as31try.pattern.staticproxy;

/**
 * Author: heaven
 * Time: 2019/10/28  16:08
 * Description:测试代理模式
 */
public class Test {

    public static void main(String[] args) {
        Canteen canteen = new Canteen();
        ProducerProxy proxy = new ProducerProxy();
        proxy.setProducer(canteen);

        proxy.sell();
    }
}
