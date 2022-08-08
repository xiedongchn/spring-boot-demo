package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;

/**
 * 有序数组的平方
 * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
 *
 * 示例 1：
 * 输入：nums = [-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 * 解释：平方后，数组变为 [16,1,0,9,100]
 * 排序后，数组变为 [0,1,9,16,100]
 *
 * 示例 2：
 * 输入：nums = [-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 *
 * 提示：
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums 已按 非递减顺序 排序
 *
 * 进阶：
 * 请你设计时间复杂度为 O(n) 的算法解决本问题
 *
 * @author xd
 */
public class DoublePointer_977 {

    /**
     * 解法一：
     * 因为数组允许存在负数，所以假设数组某个下标开始，左边的数小于0，右边的数大于等于0
     * 所以此题的关键在于原数组的值取平方之后，最大值不是在最左边就是在最右边，适合用双指针来解
     * 用两个指针分别指向数组的头和尾，每次比较左指针和右指针下标值的平方，大的值从右往左放入新数组
     * 若右边的值大，则右指针减一，若左边的值大，则左指针加一
     * 如此重复，得到的新数组会是从左往右单调递增
     *
     * 解法二：
     * 先一次遍历找到负值和正值的分界点，然后比较从左往右到分界点的值的平方和从右往左到分界点的值的平方，
     * 取大的从右往左插入新数组即可
     *
     * 第二种解法写起来比第一种方法更复杂，但是更好理解，解法一更巧妙
     *
     * 双指针的使用前提是数组有序
     */
    public int[] sortedSquares(int[] nums) {
        int i = 0, j = nums.length - 1, pos = nums.length - 1;
        int[] nums1 = new int[nums.length];
        while (i <= j) {
            int l = nums[i] * nums[i];
            int r = nums[j] * nums[j];
            if (l <= r) {
                nums1[pos] = r;
                j--;
            } else {
                nums1[pos] = l;
                i++;
            }
            pos--;
        }
        return nums1;
    }

    @Test
    public void testSearch1() {
        System.out.println(Arrays.toString(sortedSquares(new int[]{-4, -1, 0, 3, 10})));
    }
}