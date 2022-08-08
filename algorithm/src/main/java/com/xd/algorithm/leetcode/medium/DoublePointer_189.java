package com.xd.algorithm.leetcode.medium;

import org.junit.Test;

import java.util.Arrays;

/**
 * 给你一个数组，将数组中的元素向右轮转 k个位置，其中k是非负数。
 * <p>
 * 示例 1:
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 * <p>
 * 示例2:
 * 输入：nums = [-1,-100,3,99], k = 2
 * 输出：[3,99,-1,-100]
 * 解释:
 * 向右轮转 1 步: [99,-1,-100,3]
 * 向右轮转 2 步: [3,99,-1,-100]
 * <p>
 * 提示：
 * 1 <= nums.length <= 105
 * -231 <= nums[i] <= 231 - 1
 * 0 <= k <= 105
 * <p>
 * 进阶：
 * 尽可能想出更多的解决方案，至少有 三种 不同的方法可以解决这个问题。
 * 你可以使用空间复杂度为O(1) 的原地算法解决这个问题吗？
 *
 * @author xd
 */
public class DoublePointer_189 {

    public void rotate(int[] nums, int k) {
        if (k % nums.length == 0) {
            return;
        }
        k = k % nums.length;

        int l = 1;
        int temp = nums[0];
        while (++l <= nums.length) {
            int temp1 = nums[(l * k) % nums.length];
            nums[(l * k) % nums.length] = temp;
            temp = temp1;
        }
    }

    @Test
    public void testSearch1() {
        int[] nums = new int[]{-1, -100, 3, 99};
        rotate(nums, 2);
        System.out.println(Arrays.toString(nums));
    }
}