package com.thunder.as31try.pattern.decorate;

/**
 * Author: heaven
 * Time: 2019/10/28  13:57
 * Description:装饰器模式测试
 */
public class Test {

    public static void main(String[] args) {
        Man man = new Man();
        ManDecoratorA decoratorA = new ManDecoratorA();
        ManDecoratorB decoratorB = new ManDecoratorB();


        decoratorA.setPerson(man);
        decoratorB.setPerson(man);

        decoratorA.eat();
        System.out.println("\n===\n");
        decoratorB.eat();
    }
}
