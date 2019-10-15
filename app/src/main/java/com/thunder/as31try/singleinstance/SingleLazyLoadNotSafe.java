package com.thunder.as31try.singleinstance;

/**
 * Author: heaven
 * Time: 2019/10/15  17:04
 * Description: 单例 懒汉式,
 */
public class SingleLazyLoadNotSafe {

    public SingleLazyLoadNotSafe(){}
    private static SingleLazyLoadNotSafe instance;

    /*
    这种写法起到了Lazy Loading的效果，但是只能在单线程下使用。
    如果在多线程下，一个线程进入了if (singleton == null)判断语句块，还未来得及往下执行，
    另一个线程也通过了这个判断语句，这时便会产生多个实例。所以在多线程环境下不可使用这种方式。
     */
    public static SingleLazyLoadNotSafe getInstance() {
        if (instance == null){
//            synchronized (SingleLazyLoadNotSafe.class){
//                if (instance == null){ //Double-check检查 避免多线程进来时,
                    instance = new SingleLazyLoadNotSafe();
//                }
//            }
        }
        return instance;
    }

}
