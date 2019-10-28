package com.thunder.as31try.pattern.staticproxy;

/**
 * Author: heaven
 * Time: 2019/10/28  16:07
 * Description:
 */
public class ProducerProxy implements Producer {
    private Producer mProducer;

    public void setProducer(Producer producer) {
        mProducer = producer;
    }

    @Override
    public void sell() {
        System.out.println("卖货前");
        mProducer.sell();
        System.out.println("卖货后");

    }
}
