package com.hyw.as31try.algorithm;

/**
 * 猴子吃香蕉, 第一天是一半留一个, 第二天吃一半留一根
 * 请问总共28, 会吃多少天
 */
class Bannana {
    public static void main(String[] args) {
        int N = 28;
        int mid;
        int day = 0;
        int shengyu = 3;
        int sum = N;
        while (true) {
            day++;
            mid = (sum) / 2;
            int eat;
            if (mid <= shengyu) { //无法再预留, 就吃一半
                eat = mid;
            } else if (mid == 0) { //最后一天
                eat = sum;
            } else {
                eat = mid - shengyu;
            }
            if (eat <= 0) {
                break;
            }
            System.out.println("第" + day + "天: " + "总共" + sum + "根香蕉" + " 吃掉" + eat + "香蕉");
//            System.out.println("sum: " + sum + " mid: " + mid);
            System.out.println();

            sum = sum - eat;
        }
    }


}
