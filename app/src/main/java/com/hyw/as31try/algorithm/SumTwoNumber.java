package com.hyw.as31try.algorithm;

class SumTwoNumber {

    public static void main(String[] args) {
        findIndexBySum();
    }

    /*有序数组找出和为N的两个元素*/
    private static void findIndexBySum() {
        int[] array = {0, 0, 1, 2, 5, 6};
        int target = 6;

        /*
        使用双指针，一个指针指向值较小的元素，一个指针指向值较大的元素。指向较小元素的指针从头向尾遍历，指向较大元素的指针从尾向头遍历。
        如果两个指针指向元素的和 sum == target，那么得到要求的结果；
        如果 sum > target，移动较大的元素，使 sum 变小一些；
        如果 sum < target，移动较小的元素，使 sum 变大一些。
         */
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            int sum = array[start] + array[end];
            if (sum == target) {
                System.out.println(start + " " + end);
                start++;
//                break;
            } else if (sum < target) {
                start++;
            } else {
                end--;
            }
        }


    }


}
