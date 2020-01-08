package com.thunder.as31try.ndk;

/**
 * 测试native回调java的方法
 */
public class TestCallback {

    String updateResult(int type){
        System.out.println("updateResult " + type);
        return ""+(type + 1);
    }
    native void doGetData(int type);

    public static void main(String[] args) {

        System.load("//Users//heyuwen//Documents//work//clion//two//cmake-build-debug//libtwo.dylib");
//        native方法doGetData 会调用updateResult方法, 获取返回值
        new TestCallback().doGetData(44);
    }
}
