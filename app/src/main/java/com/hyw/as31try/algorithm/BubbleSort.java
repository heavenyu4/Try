package com.hyw.as31try.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序算法
 * 依次比较相邻的2个数, 大的数据往后排
 * 稳定排序算法
 */
class BubbleSort {

    public static void main(String[] args) {

        int[] in = new int[]{34, 23, 44, 22, 13, 67};
        for (int i = 0; i < in.length - 1; i++) {
            boolean hasChanged = false;
            for (int j = 0; j < in.length - 1 - i; j++) {
                if (in[j] > in[j + 1]) {
                    int tmp = in[j + 1];
                    in[j + 1] = in[j];
                    in[j] = tmp;
                    hasChanged = true;
                }
            }
            if (!hasChanged) {
                //没有可交换的, 排序完毕, 退出
                break;
            }

            System.out.println(Arrays.toString(in));
        }

    }
}
