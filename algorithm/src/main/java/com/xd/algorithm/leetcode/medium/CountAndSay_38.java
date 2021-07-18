package com.xd.algorithm.leetcode.medium;

import org.junit.Test;

/**
 * 外观数列
 * @author xd
 */
public class CountAndSay_38 {

    public String solution1(int n) {
        if (n < 1) {
            return "";
        } else if (n == 1) {
            return "1";
        }
        StringBuilder builder = new StringBuilder("1");
        for (int i = 1; i < n; i++) {
            int index = 0;
            int count = 1;
            int len = builder.length();
            for (int j = 1; j < len; j++) {
                if (builder.charAt(index) == builder.charAt(j)) {
                    count++;
                } else {
                    count = count == 0 ? 1 : count;
                    builder.append((char) (count + 48));
                    builder.append(builder.charAt(index));
                    count = 1;
                    index = j;
                }
            }
            if (count > 0) {
                builder.append((char) (count + 48));
                builder.append(builder.charAt(index));
            }
            builder.delete(0, len);
        }
        return builder.toString();
    }

    @Test
    public void testSolution1() {
        System.out.println(solution1(10));
    }
}
