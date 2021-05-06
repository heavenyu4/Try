package com.hyw.as31try.proxyinstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxyVendor {

    public interface Sell {
        void sell();

        void ad();
    }

    public static class Vender implements Sell {

        @Override
        public void sell() {
            System.out.println("vender sell");
        }

        @Override
        public void ad() {
            System.out.println("vender ad");

        }
    }


    public static void main(String[] args) {
         final Vender vender = new Vender();

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Sell sell = (Sell) Proxy.newProxyInstance(Vender.class.getClassLoader(),
                Vender.class.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("before");
                        Object invoke = method.invoke(vender, args);
                        System.out.println("after");
                        return invoke;
                    }
                });

        sell.ad();
        sell.sell();
    }

}
