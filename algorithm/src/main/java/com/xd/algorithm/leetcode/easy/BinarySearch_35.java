package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * 搜索插入位置
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(log n) 的算法。
 * <p>
 * 提示:
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums 为无重复元素的升序排列数组
 * -104 <= target <= 104
 *
 * @author xd
 */
public class BinarySearch_35 {

    /**
     * 如果找到目标值，则直接返回，最原始的二分查找逻辑
     * 此题的关键在于，未找到目标值时，left必然等于right，此时该如何处理
     * 1、若nums[left] > target，则target应插入到left的位置，返回left
     * 2、若nums[left] < target，则target应插入到left + 1的位置，返回left + 1
     * 当left = right，此时1和2恰好就是两个else if小括号中的的条件语句
     * 循环的结束条件是否应该让left等于right就清楚了
     * 循环结束时，直接返回left即可
     *
     * @param nums   数组
     * @param target 目标值
     * @return 目标下标
     */
    public int searchInsert(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = left + 1;
            }
        }
        return left;
    }

    @Test
    public void testSearch1() {
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));
    }
}