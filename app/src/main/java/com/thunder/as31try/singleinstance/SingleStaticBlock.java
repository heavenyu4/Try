package com.thunder.as31try.singleinstance;

/**
 * Author: heaven
 * Time: 2019/10/15  16:56
 * Description:单例 饿汉式, 静态代码块
 */
public class SingleStaticBlock {

    private SingleStaticBlock(){}
    private static SingleStaticBlock instance;

    static{
        instance = new SingleStaticBlock();
    }

    public  static SingleStaticBlock getInstance(){
        return instance;
    }
}
