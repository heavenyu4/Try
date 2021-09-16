package com.hyw.as31try.interview;

import java.util.Arrays;

/**
 * Author: heaven
 * Time: 2019/11/25  21:47
 * Description: 二分法查找 可以参考Arrays.binarySearch方法
 */
public class TestBinarySearch {

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 70};
        Arrays.sort(arr);
        int search = binarySearch(arr, 70);
        System.out.println("search: " + search);
    }

    private static int binarySearch(int[] arr, int value) {
        int left = 0;
        int right = arr.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            System.out.println(" left: " + left + " right: " + right
                    + " mid: " + mid
            );
            System.out.println(" leftV: " + arr[left] + " rightV: " + arr[right]
                    + " midV: " + arr[mid] + " value: " + value
            );
            if (value > arr[mid]) {
                left = mid + 1;
            } else if (value < arr[mid]) {
                right = mid - 1;
            } else {
                return mid;
            }

        }
        return -1;
    }

}
