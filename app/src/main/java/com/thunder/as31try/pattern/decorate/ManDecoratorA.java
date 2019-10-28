package com.thunder.as31try.pattern.decorate;

/**
 * Author: heaven
 * Time: 2019/10/28  13:56
 * Description:具体装饰者
 */
public class ManDecoratorA extends Decorator {

    @Override
    public void eat() {
        super.eat();
        System.out.println("ManDecoratorA ");
    }
}
