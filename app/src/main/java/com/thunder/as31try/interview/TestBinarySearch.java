package com.thunder.as31try.interview;

import java.util.Arrays;

/**
 * Author: heaven
 * Time: 2019/11/25  21:47
 * Description: 二分法查找 可以参考Arrays.binarySearch方法
 */
public class TestBinarySearch {

    public static void main(String[] args) {
        int [] arr = {20,30,70,10,90};
        Arrays.sort(arr);
        int search = binarySearch(arr, 10);
        System.out.println("search: " + search);
    }

    private static int binarySearch(int [] arr, int value){
        int left = 0;
        int right = arr.length -1;
        int mid = 0;
        while (left < right){
            mid = (left + right) /2;
            if (arr[mid] <value){
                left = mid;
            }else if(arr[mid] > value){
                right = mid;
            }else{
                return mid;
            }

        }
        return -1;
    }

}
