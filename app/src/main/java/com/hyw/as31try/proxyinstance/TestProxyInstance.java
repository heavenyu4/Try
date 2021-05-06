package com.hyw.as31try.proxyinstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试ArrayList的动态代理
 */
public class TestProxyInstance {

    public static void main(String[] args) {
        final List<String> list = new ArrayList<>();

        List<String> proxyInstance = (List<String>) Proxy.newProxyInstance(ArrayList.class.getClassLoader(),
                ArrayList.class.getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(list, args);
                    }
                });
        proxyInstance.add("he");
        proxyInstance.add("cai");
        proxyInstance.add("who");
        System.out.println(proxyInstance);


    }
}
