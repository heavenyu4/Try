package com.thunder.as31try.ndk;

public class TestStringFunc {

    native String getCatResult(String ori);

    public static void main(String[] args) {
        System.load("//Users//heyuwen//Documents//work//clion//two//cmake-build-debug//libtwo.dylib");
        String catResult = new TestStringFunc().getCatResult(" World");
        System.out.println(catResult);

    }
}
