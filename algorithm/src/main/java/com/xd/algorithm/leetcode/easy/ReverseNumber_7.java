package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * @author xd
 * Created on 2021/6/21
 */
public class ReverseNumber_7 {

    public int solution1(int x) {
        // Integer.MAX_VALUE = 2147483647
        // Integer.MIN_VALUE = -2147483648
        if (x == 0 || x == Integer.MIN_VALUE) {
            return 0;
        }
        boolean isNegative = false;
        if (x < 0) {
            isNegative = true;
            x = -x;
        }
        char[] s = String.valueOf(x).toCharArray();
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = temp;
        }
        long x1 = Long.parseLong(String.valueOf(s));
        x1 = isNegative ? -x1 : x1;
        return x1 > Integer.MAX_VALUE ? 0 : (int) (x1 < Integer.MIN_VALUE ? 0 : x1);
    }

    /**
     * 想办法每次取到数字的最低位，乘10之后再加上一个最低位，即可反转
     */
    public int solution2(int x) {
        // 其实这里是不能用long的，因为题干要求不能存储64位整数
        long result = 0L;
        while (x != 0) {
            result = result * 10 + x % 10;
            x /= 10;
        }
        return result > Integer.MAX_VALUE ? 0 : result < Integer.MIN_VALUE ? 0 : (int) result;
    }

    /**
     * 想办法每次取到数字的最低位，乘10之后再加上一个最低位，即可反转
     */
    public int solution3(int x) {
        int res = 0;
        while (x != 0) {
            int t = x % 10;
            int newRes = res * 10 + t;
            // 如果反向推导的值不等于res，意味着newRes溢出了才会导致值不等，直接返回0
            // 这个条件可能是有bug的，未验证
            if ((newRes - t) / 10 != res)
                return 0;
            res = newRes;
            x = x / 10;
        }
        return res;
    }

    /**
     * 想办法每次取到数字的最低位，乘10之后再加上一个最低位，即可反转
     */
    public int solution4(int x) {
        // Integer.MAX_VALUE = 2147483647
        // Integer.MIN_VALUE = -2147483648
        int res = 0;
        while (x != 0) {
            // 每次取末尾数字
            int t = x % 10;
            // 判断是否 大于 最大32位整数
            if (res > 214748364 || (res == 214748364 && t > 7)) {
                return 0;
            }
            // 判断是否 小于 最小32位整数
            if (res < -214748364 || (res == -214748364 && t < -8)) {
                return 0;
            }
            res = res * 10 + t;
            x /= 10;
        }
        return res;
    }

    /**
     * 本质上就是solution4，只是用MAX_VALUE和MIN_VALUE替换了常数
     */
    public int solution5(int x) {
        // Integer.MAX_VALUE = 2147483647
        // Integer.MIN_VALUE = -2147483648
        int res = 0;
        int max = Integer.MAX_VALUE / 10;
        int min = Integer.MIN_VALUE / 10;
        int maxTail = Integer.MAX_VALUE % 10;
        int minTail = Integer.MIN_VALUE % 10;
        while (x != 0) {
            // 每次取末尾数字
            int t = x % 10;
            // 判断是否 大于 最大32位整数
            if (res > max || (res == max && t > maxTail)) {
                return 0;
            }
            // 判断是否 小于 最小32位整数
            if (res < min || (res == min && t < minTail)) {
                return 0;
            }
            res = res * 10 + t;
            x /= 10;
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println(-1 % 5);
    }
}
