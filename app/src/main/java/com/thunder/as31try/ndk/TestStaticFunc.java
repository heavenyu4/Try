package com.thunder.as31try.ndk;

/**
 * 测试静态注册
 */
public class TestStaticFunc {

    public static native String hello();

    public static void main(String[] args) {

        System.load("//Users//heyuwen//Documents//work//clion//two//cmake-build-debug//libtwo.dylib");

        String hello = new TestStaticFunc().hello();
        System.out.println(hello);
    }
}
