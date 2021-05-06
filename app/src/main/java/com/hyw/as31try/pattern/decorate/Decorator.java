package com.hyw.as31try.pattern.decorate;

/**
 * Author: heaven
 * Time: 2019/10/28  13:54
 * Description:抽象装饰者
 */
//一个抽象类如果没有抽象方法，是可以定义为抽象类的。这么做的目的只有一个，
// 就是不让其他类创建本类对象，交给子类完成。
public abstract class Decorator implements Person {
    protected Person mPerson;

    public void setPerson(Person person) {
        mPerson = person;
    }

    @Override
    public void eat() {
        mPerson.eat();
    }
}
