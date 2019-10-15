package com.thunder.as31try.singleinstance;

/**
 * Author: heaven
 * Time: 2019/10/15  17:04
 * Description: 单例 懒汉式, Double-check检查
 * Double-Check概念对于多线程开发者来说不会陌生，如代码中所示，我们进行了两次if (singleton == null)检查，这样就可以保证线程安全了。
 * 这样，实例化代码只用执行一次，后面再次访问时，判断if (singleton == null)，直接return实例化对象。
 */
public class SingleLazyLoad {

    private SingleLazyLoad(){}
    private static SingleLazyLoad instance;

    public static SingleLazyLoad getInstance() {
        if (instance == null){
            synchronized (SingleLazyLoad.class){
                if (instance == null){ //Double-check检查 避免多线程进来时,
                    instance = new SingleLazyLoad();
                }
            }
        }
        return instance;
    }

}
