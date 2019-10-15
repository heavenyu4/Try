package com.thunder.as31try.singleinstance;

/**
 * Author: heaven
 * Time: 2019/10/15  17:09
 * Description:测试多线程的单例是不是真正的单例
 */
public class TestSingle {

    static class MyRun implements  Runnable{
        String name;

        public MyRun(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10; j++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("name: " + name + "  "+j + " " + SingleInnerClass.getInstance());
            }
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new  Thread(new MyRun("test" + i)).start();
//            System.out.println(new SingleLazyLoadNotSafe());
        }
    }
}
