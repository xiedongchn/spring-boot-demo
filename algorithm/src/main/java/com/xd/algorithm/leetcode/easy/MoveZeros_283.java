package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序
 * <p>
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 *
 * @author xd
 */
public class MoveZeros_283 {

    @Test
    public void test() {
        solution1(new int[]{1, 0, 1});
    }

    public void solution1(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int temp;
        // i是前指针，j是后指针，后指针是每次都要移动的，所以j++
        for (int i = 0, j = 1; j < nums.length && i <= j; j++) {
            // 前指针为0时，除非发生交换，否则不移动
            if (nums[i] == 0) {
                // 后指针不为0时，交换，交换完成，前后指针右移1位
                // 后指针也为0，不交换，同时只有后指针移动
                if (nums[j] != 0) {
                    temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    // 发生交换，前指针移动
                    i++;
                }
            } else {
                // 前指针不为0，往后移动一位
                i++;
            }
        }
    }

    /**
     * 下面这个解法是不对的，虽然把所有0都移到了右边，但是没有保证非0元素的顺序
     */
    public void solution2(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        int temp;
        for (int i = 0, j = nums.length - 1; i <= j; ) {
            if (nums[i] == 0 && nums[j] != 0) {
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
                j--;
            } else if (nums[i] != 0) {
                i++;
            } else if (nums[j] == 0) {
                j--;
            }
        }
    }
}
