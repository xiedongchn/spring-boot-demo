package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 存在重复元素
 *
 * @author xd
 * Created on 2021/6/22
 */
public class ContainsDuplicate_217 {

    public boolean solution1(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }

        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (!set.add(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean solution2(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i <= nums.length - 2; i++) {
            // 遍历保证相邻元素：前小后大即为不同 反之true
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void test() {
        System.out.println(solution2(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2}));
    }
}
