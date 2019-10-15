package com.thunder.as31try.singleinstance;

/**
 * Author: heaven
 * Time: 2019/10/15  16:53
 * Description: 单例 饿汉式 静态常量
 * 单例的8种写法 https://www.cnblogs.com/zhaoyan001/p/6365064.html
 */
public class SingleStatic {

    private static final SingleStatic mSingleStatic = new SingleStatic();
    private SingleStatic(){}

    public static SingleStatic getInstance(){
        return mSingleStatic;
    }
}
