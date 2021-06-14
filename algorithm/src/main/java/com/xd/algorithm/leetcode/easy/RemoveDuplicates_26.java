package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 *
 * @author xd
 */
public class RemoveDuplicates_26 {

    public int removeDuplicates1(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int length = 1;
        for (int i = 1; i < nums.length; i++) {
            while (nums[i] == nums[length - 1]) {
                i++;
                if (i >= nums.length) {
                    return length;
                }
            }
            nums[length] = nums[i];
            length++;
        }
        return length;
    }

    public int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return 0;
        }
        int length = 1;
        Set<Integer> set = new HashSet<>();
        set.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (!set.contains(nums[i])) {
                set.add(nums[i]);
                nums[length] = nums[i];
                length++;
            }
        }
        return length;
    }

    public int removeDuplicates3(int[] nums) {
        if (nums == null) {
            return 0;
        } else if (nums.length < 2) {
            return nums.length;
        }
        int length = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[length] != nums[i]) {
                nums[++length] = nums[i];
            }
        }
        return ++length;
    }

    @Test
    public void testRemoveDuplicates() {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(removeDuplicates3(nums) + ":" + Arrays.toString(nums));
        nums = null;
        System.out.println(removeDuplicates3(nums) + ":" + Arrays.toString(nums));
        nums = new int[]{0, 2};
        System.out.println(removeDuplicates3(nums) + ":" + Arrays.toString(nums));
        nums = new int[]{0, 1, 1, 1, 1};
        System.out.println(removeDuplicates3(nums) + ":" + Arrays.toString(nums));
        nums = new int[]{1, 1};
        System.out.println(removeDuplicates3(nums) + ":" + Arrays.toString(nums));
    }
}
