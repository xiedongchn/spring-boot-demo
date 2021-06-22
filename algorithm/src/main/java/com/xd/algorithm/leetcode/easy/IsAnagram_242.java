package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * 有效的字母异位词
 *
 * @author xd
 * Created on 2021/6/22
 */
public class IsAnagram_242 {

    public boolean solution1(String s, String t) {
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() != t.length()) {
            return false;
        }
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        int[] s2 = new int[26];
        int[] t2 = new int[26];
        for (int i = 0; i < s1.length; i++) {
            s2[s1[i] - 'a'] += 1;
            t2[t1[i] - 'a'] += 1;
        }
        for (int i = 0; i < s2.length; i++) {
            if (s2[i] != t2[i]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        System.out.println(solution1("nl", "cx"));
    }
}
