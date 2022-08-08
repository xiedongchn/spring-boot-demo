package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * 给定一个n个元素有序的（升序）整型数组nums 和一个目标值target
 * 写一个函数搜索nums中的 target，如果目标值存在返回下标，否则返回 -1。
 * <p>
 * 关于二分法区间开闭，下面这个题解解释的非常好
 * https://leetcode-cn.com/problems/binary-search/solution/er-fen-cha-zhao-xiang-jie-by-labuladong/
 *
 * @author xd
 */
public class BinarySearch_704 {

    public int search(int[] nums, int target) {
        // 这里定义的查找区间是[0, length -1]，两端都是闭合的
        int left = 0, right = nums.length - 1;
        // 由于两端闭合，所以循环的结束条件就是左边界大于右边界
        while (left <= right) {
            // mid = (left + right) / 2 = (2left + right - left) / 2 = left + (right - left) / 2
            // 下面的算法是按照上面的算法演化来的，目的是为了尽可能降低left+right造成的整型越界，并不能完全避免
            // 除以2相当于位运算右移一位，要注意，移位运算符的优先级笔加运算符要低，所以需要加上括号
            int mid = left + ((right - left) >> 1);
            // 等于target就直接返回下标值
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                // 中间值小于目标值，调整左边界，因为mid已经搜索过，所以left = mid + 1
                left = mid + 1;
            } else if (nums[mid] > target) {
                // 中间值大于目标值，调整右边界，因为mid已经搜索过，所以right = mid - 1
                right = mid - 1;
            }
        }
        return -1;
    }

    @Test
    public void testSearch1() {
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 9));
    }
}