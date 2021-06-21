package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * @author xd
 * Created on 2021/6/21
 */
public class ReverseString_344 {

    /**
     * 双指针解法，一个指针从前往后，一个指针从后往前，两两交换
     *
     * @param s 字符数组
     */
    public void solution1(char[] s) {
        if (s == null || s.length < 2) {
            return;
        }

        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - i - 1];
            s[s.length - i - 1] = temp;
        }
    }

    /**
     * offer消失法，s.reverse()完事
     *
     * @param s 字符数组
     */
    public void solution2(char[] s) {

    }

    @Test
    public void test() {
        char[] s = new char[]{'h', 'e', 'l', 'l', 'o'};
        solution1(s);
        System.out.println(s);
    }
}
