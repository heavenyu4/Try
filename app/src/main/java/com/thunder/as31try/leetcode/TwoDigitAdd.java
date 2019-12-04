package com.thunder.as31try.leetcode;

import java.util.Arrays;

/**
 * Author: heaven
 * Time: 2019/12/3  15:29
 * Description:
 * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。
 * 输入: 38
 * 输出: 2
 * 解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。
 * 你可以不使用循环或者递归，且在 O(1) 时间复杂度内解决这个问题吗？
 */
public class TwoDigitAdd {

    public static  int addDigits(int num) {
        int [] param = new int[10];
        int ge = num % 10 ;
        int shi = num /10;
        int i = 0;
        while(shi != 0){
            param[i++] = ge;
            ge = shi %10;
            shi = shi / 10;
        }
        param[i++] = ge;
        int sum = 0;
        for (int j = 0; j < i; j++) {
            sum += param[j];
        }

        System.out.println(Arrays.toString(param));
        System.out.println(sum % 10);
        return 0;
    }

    public static void main(String[] args) {
        addDigits(38);
    }
}
