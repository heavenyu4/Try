package com.thunder.as31try.executordemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

    private static int num = 0;

    public static void main(String[] args) {
        final String TAG = "tag";
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 11; i++) {
            if (i <=3){
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("run: "+ num++);
                    }
                });
            }else if(i <=8){

                /*
                submit方法返回的事future，这个如果出现异常是没有打印的
                因为如果用submit方法，需要加try...catch
                如果用execute方法的话，还是会打印异常错误栈信息的
                但是这2个方法都没有发现后边的任务没有执行的情况，还是会顺序执行的
                 */
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("run: " + num++ + 1 / 0);
                        }catch(Exception e){
                            System.out.println("run: " +  e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });
            }else{
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("run: "+ num++);
                    }
                });
            }
        }

    }
}
