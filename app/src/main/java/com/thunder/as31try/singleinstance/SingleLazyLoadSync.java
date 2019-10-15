package com.thunder.as31try.singleinstance;

/**
 * Author: heaven
 * Time: 2019/10/15  17:04
 * Description: 单例 懒汉式, 加同步函数锁
 *效率太低了，每个线程在想获得类的实例时候，
 * 执行getInstance()方法都要进行同步。而其实这个方法只执行一次实例化代码就够了，
 * 后面的想获得该类实例，直接return就行了。方法进行同步效率太低要改进。
 */
public class SingleLazyLoadSync {

    private SingleLazyLoadSync(){}
    private static SingleLazyLoadSync instance;

    public synchronized static SingleLazyLoadSync getInstance() {
        if (instance == null){
//            synchronized (SingleLazyLoadSync.class){
//                if (instance == null){ //Double-check检查 避免多线程进来时,
                    instance = new SingleLazyLoadSync();
//                }
//            }
        }
        return instance;
    }

}
