package com.thunder.as31try.pattern.staticproxy;

/**
 * Author: heaven
 * Time: 2019/10/28  16:06
 * Description:
 */
public class Canteen implements Producer {
    @Override
    public void sell() {
        System.out.println("小卖部卖货");
    }
}
