package com.thunder.as31try.pattern.decorate;

/**
 * Author: heaven
 * Time: 2019/10/28  13:53
 * Description:装饰器模式- 具体组件
 */
public class Man implements Person {
    @Override
    public void eat() {
        System.out.println("man eat!");
    }
}
