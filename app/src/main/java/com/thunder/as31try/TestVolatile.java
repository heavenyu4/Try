package com.thunder.as31try;

/**
 * Author: heaven
 * Time: 2019/10/16  17:27
 * Description:测试volatile变量
 * 在 java 的内存模型中每一个线程运行时都有一个线程栈，线程栈保存了线程运行时候变量值信息。当线程访问
 * 某一个对象时候值的时候，首先通过对象的引用找到对应在堆内存的变量的值，然后把堆内存变量的具体值 load 到线
 * 程本地内存中，建立一个变量副本，之后线程就不再和对象在堆内存变量值有任何关系，而是直接修改副本变量的值，
 * 在修改完之后的某一个时刻（线程退出之前），自动把线程变量副本的值回写到对象在堆中变量。这样在堆中的对象
 * 的值就产生变化了。
 * 也就是说上面主函数中开启了 1000 个子线程，每个线程都有一个变量副本，每个线程修改变量只是临时修改了
 * 自己的副本，当线程结束时再将修改的值写入在主内存中，这样就出现了线程安全问题。因此结果就不可能等于 1000
 * 了，一般都会小于 1000。
 * read->load->use->asign->store->write
 *
 */
public class TestVolatile {

    public static class Counter {
        private volatile int count = 0;

        public void inc() {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }

        @Override
        public String toString() {
            return "[count=" + count + "]";
        }
    }

    //---------------------------------华丽的分割线-----------------------------
    public static void main(String[] args) {
        final Counter counter = new Counter();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    counter.inc();
                }
            }).start();
        }
        System.out.println(counter);
    }
}
