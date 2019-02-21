package com.thunder.as31try.threadlocaltest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalDemo {

    public static void main(String [] args){
        ThreadLocal<Date> sDateThreadLocal = new ThreadLocal<Date>() {
            @Override
            protected Date initialValue() {
                return new Date();
            }
        };

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 5; i++) {
            Date date = sDateThreadLocal.get();
            System.out.println(sdf.format(date));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
