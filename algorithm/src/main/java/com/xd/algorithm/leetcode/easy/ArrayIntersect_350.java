package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.*;

/**
 * 找出两个数组的交集，输出的内容不需要是有序的
 * 例子：输入[1, 2, 2, 1]，[2, 2]，输出[2, 2]
 * 例子：输入[9,4,9,8,4]，[4, 9, 5]，输出[4, 9]
 *
 * @author xd
 */
public class ArrayIntersect_350 {

    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[0];
        }
        Map<Integer, Integer> map = new HashMap<>();
        if (nums1.length > nums2.length) {
            int[] nums3 = nums1;
            nums1 = nums2;
            nums2 = nums3;
        }
        Integer k;
        for (int j : nums1) {
            k = map.get(j);
            if (k != null) {
                map.put(j, ++k);
            } else {
                map.put(j, 1);
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int j : nums2) {
            k = map.get(j);
            if (k != null) {
                if (k > 0) {
                    k--;
                    map.put(j, k);
                    list.add(j);
                }
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @Test
    public void testIntersect1() {
        int[] nums1 = new int[]{1, 2, 2, 1};
        int[] nums2 = new int[]{2,2};
        System.out.println(Arrays.toString(intersect(nums1, nums2)));

        nums1 = new int[]{4,9,5};
        nums2 = new int[]{9,4,9,8,4};
        System.out.println(Arrays.toString(intersect(nums1, nums2)));

        nums1 = new int[]{84,84};
        nums2 = new int[]{84,84,84,84};
        System.out.println(Arrays.toString(intersect(nums1, nums2)));
    }
}
