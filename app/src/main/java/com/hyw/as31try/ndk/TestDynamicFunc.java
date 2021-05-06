package com.hyw.as31try.ndk;

/***
 * 测试动态注册
 */
public class TestDynamicFunc {

    public native void dynFunc1();
    public native String dynFunc2();

    public static void main(String[] args) {

        System.load("/Users/heyuwen/Documents/work/clion/two/cmake-build-debug/libtwo_dyn.dylib");

        TestDynamicFunc testDynamicFunc = new TestDynamicFunc();
        testDynamicFunc.dynFunc1();
        String result = testDynamicFunc.dynFunc2();
        System.out.println(result);

    }

}
