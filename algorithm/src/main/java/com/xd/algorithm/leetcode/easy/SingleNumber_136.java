package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 数组中每个数字都出现了两次，找出只出现了一次的那个数字
 *
 * @author xd
 */
public class SingleNumber_136 {

    public int singleNumber1(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        Map<Integer, Integer> exists = new HashMap<>();
        Map<Integer, Integer> notExists = new HashMap<>();
        boolean e;
        boolean ne;
        for (int i : nums) {
            e = exists.containsKey(i);
            ne = notExists.containsKey(i);
            if (!e && !ne) {
                notExists.put(i, i);
            } else if (ne) {
                notExists.remove(i);
                exists.put(i, i);
            }
        }
        return (int) notExists.keySet().toArray()[0];
    }

    /**
     * 只出现一次的数字
     *
     * @param nums 数组
     * @return 返回只出现一次的元素值
     */
    public int singleNumber2(int[] nums) {
        for (int i = 1; i< nums.length;i++) {
            nums[0] ^= nums[i];
        }
        return nums[0];
    }

    @Test
    public void testSingleNumber1() {
        int[] nums = new int[]{4, 1, 2, 1, 2};
        System.out.println(singleNumber2(nums));
    }
}
