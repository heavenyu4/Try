package com.hyw.as31try.ndk;

/**
 * 测试native野指针
 */
public class TestWildPointer {
    native String getString(int len);

    public static void main(String[] args) {
        System.load("//Users//heyuwen//Documents//work//clion//two//cmake-build-debug//libtwo.dylib");
        TestWildPointer testWildPointer = new TestWildPointer();
        testWildPointer.getString(10);
        testWildPointer.getString(20);
    }

}
