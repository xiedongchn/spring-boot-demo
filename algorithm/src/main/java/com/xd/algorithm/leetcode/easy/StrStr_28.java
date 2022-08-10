package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * @author xd
 * Created on 2021/7/15
 */
public class StrStr_28 {

    public int solution1(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return -1;
        } else if ("".equals(needle)) {
            return 0;
        }

        char[] arr1 = haystack.toCharArray();
        char[] arr2 = needle.toCharArray();
        // len2 = 3
        int len2 = arr2.length;
        // i < 5 -3 -> i < 2
        for (int i = 0; i < arr1.length - len2; i++) {
            // j = 3
            int j = i + len2;
            boolean flag = true;
            // k <= 1
            for (int k = 0; k <= len2 / 2; k++) {
                // arr1[0 + 0] != arr2[0] || arr1[3 - 0] != arr2[3 - 0]
                // arr1[0 + 1] != arr2[1] || arr1[3 - 1] != arr2[3 - 0]
                if (arr1[i + k] != arr2[k] || arr1[j - k] != arr2[len2 - k - 1]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
        return -1;
    }

    @Test
    public void testSolution1() {
        String haystack = "hello", needle = "ll";
        System.out.println(solution1(haystack, needle));
    }
}
