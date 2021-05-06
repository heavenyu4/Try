package com.hyw.as31try.testMultiThread;

import java.util.concurrent.Semaphore;

/**
 * Author: heaven
 * Time: 2019/10/14  21:10
 * Description: 测试3个线程, A线程初始化一个变量, B,C线程要获取这个初始化的结果
 * 这里利用信号量实现
 */
public class TestSemaphore {


    static int num = 0;
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(0);

        Thread A  = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num = 5;
                semaphore.release(2);
            }
        };

        Thread B = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B: run: " + num);
            }
        };
        Thread C = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("C: run: " + num);
            }
        };

        A.start();
        B.start();
        C.start();
    }

}
