package com.hyw.as31try.leetcode;

/**
 * 35. 搜索插入位置
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 你可以假设数组中无重复元素。
 *
 * 示例 1:
 *
 * 输入: [1,3,5,6], 5
 * 输出: 2
 * 示例 2:
 *
 * 输入: [1,3,5,6], 2
 * 输出: 1
 * 示例 3:
 *
 * 输入: [1,3,5,6], 7
 * 输出: 4
 * 示例 4:
 *
 * 输入: [1,3,5,6], 0
 * 输出: 0
 */
public class InsertPosition {

    public int searchInsert(int[] nums, int target) {
        int length = nums.length;
        if (length == 0){
            return -1;
        }
        if (target <= nums[0]){
            return 0;
        }else if (target >= nums[length -1]){
            return length;
        }else{
            int leftDiff = target - nums[0];
            int rightDiff = 0;
            for (int i = 1; i < length - 1; i++) {
                leftDiff = target - nums[i -1];
                rightDiff = target - nums[i];
                if (rightDiff == 0){
                    return i;
                }else if(leftDiff > 0 && rightDiff < 0){
                    return i;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        InsertPosition position = new InsertPosition();
        int [] nums = new int []{1,3,5,6};
        long startTime = System.currentTimeMillis();
        int pos = position.searchInsert(nums, 5);
        System.out.println(pos);
        long cost = (System.currentTimeMillis() - startTime) ;
        System.out.println(cost + "ms");


    }
}
