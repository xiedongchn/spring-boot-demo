package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * 判断字符串是否回文串
 *
 * @author xd
 * Created on 2021/6/22
 */
public class IsPalindrome_125 {

    public boolean solution1(String s) {
        if (s == null || s.length() < 2) {
            return true;
        }
        int diff = 'a' - 'A';
        char[] c = s.toCharArray();
        // 定义左右指针
        int left = 0;
        int right = c.length - 1;
        while (left < right) {
            // 小写转大写
            if (c[left] >= 'a' && c[left] <= 'z') {
                c[left] = (char) (c[left] - diff);
            }
            // 小写转大写
            if (c[right] >= 'a' && c[right] <= 'z') {
                c[right] = (char) (c[right] - diff);
            }
            // 不是大写字母也不是数字，则跳过
            if (!(c[left] >= 'A' && c[left] <= 'Z') && !(c[left] >= '0' && c[left] <= '9')) {
                left++;
                continue;
            }
            // 不是大写字母也不是数字，则跳过
            if (!(c[right] >= 'A' && c[right] <= 'Z') && !(c[right] >= '0' && c[right] <= '9')) {
                right--;
                continue;
            }
            // 左边不等于右边，则不是回文
            if (c[left] != c[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public boolean solution2(String s) {
        if (s == null || s.length() == 0)
            return true;
        s = s.toLowerCase();
        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            while (i < j && !Character.isLetterOrDigit(s.charAt(i)))
                i++;
                while (i < j && !Character. isLetterOrDigit(s.charAt(j)))
                j--;
            if (s.charAt(i) != s.charAt(j))
                return false;
        }
        return true;
    }

    @Test
    public void test() {
        System.out.println(solution1("0P"));
        System.out.println(solution1("A man, a plan, a canal: Panama"));
    }
}
