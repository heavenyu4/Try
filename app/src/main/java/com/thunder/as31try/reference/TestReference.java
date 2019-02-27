package com.thunder.as31try.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 测试引用类型
 * 强引用，永远不会被GC回收，直至OOM
 * 软引用，系统内存不足时，回收
 * 虚引用，系统调用gc时，
 */
public class TestReference {

    public static void main(String[] args) {
        String str = new String("adb");  //存储在堆中
//        String str = "abd";  //存在常量池中
        SoftReference<String> softReference = new SoftReference<>(str);
        WeakReference<String> weakReference = new WeakReference<>(str);

        System.out.println("soft: "+ softReference.get());
        System.out.println("weak: " + weakReference.get());

        //这个不能用，相当于把自己的引用去掉了
//        softReference = null;

        //去除强引用链
        str = null;

        //清除软引用的引用链
        softReference.clear();
        System.out.println("gc, soft: " + softReference.get());

        //清除虚引用的引用链
        System.gc();
        System.out.println("gc, weak: " + weakReference.get());
    }
}
