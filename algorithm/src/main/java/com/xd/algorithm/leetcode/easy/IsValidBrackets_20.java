package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.Stack;

/**
 * 是否合法的括号，例如，输入：
 * "()"，返回true
 * "()[]{}"，返回true
 * "(]"，返回false
 * "([)]"，返回false
 * "{[]}"，返回true
 * "))"，返回false
 * "(("，返回false
 *
 * @author xd
 */
public class IsValidBrackets_20 {

    public boolean isValidBrackets(String s) {
        if (s == null || s.length() < 2) {
            return false;
        }
        char[] charArray = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (char c : charArray) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
                continue;
            }
            // 如果是空栈，说明没有与之匹配的左括号
            if (stack.size() <= 0) {
                return false;
            }
            char p = stack.pop();
            if (p == '(' && c != ')' || p == '[' && c != ']' || p == '{' && c != '}') {
                return false;
            }
        }
        //return pushCount == popCount;// 记录入栈和出栈次数是否一直，如果不一致，说明不匹配
        // 不用通过计数器记录出入栈次数，直接判断栈是否是空的，如果是空的说明全匹配上了
        return stack.isEmpty();
    }

    public boolean isValidBrackets1(String s) {
        int len;
        do {
            len = s.length();
            // 逻辑上没错，代码也很简洁，但是replace本身的操作是 O(n) 的，导致算法的最坏时间复杂度是 O(n^2)
            s = s.replace("()", "").replace("[]", "").replace("{}", "");
        } while (len != s.length());
        return s.length() == 0;
    }

    @Test
    public void testIsValidBrackets() {
        System.out.println(isValidBrackets("()"));
        System.out.println(isValidBrackets("()[]{}"));
        System.out.println(isValidBrackets("(]"));
        System.out.println(isValidBrackets("([)]"));
        System.out.println(isValidBrackets("{[]}"));
    }
}
