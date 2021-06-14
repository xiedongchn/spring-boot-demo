package com.xd.algorithm.leetcode.easy;

/**
 * @author xd
 */
public class PlusOne_66 {

    public int[] solution1(int[] digits) {
        // 优化点1：如果最低位不是9，直接加1返回，避免走进循环，增加内存消耗
        if (digits[digits.length - 1] < 9) {
            digits[digits.length - 1] += 1;
            return digits;
        }
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
                // i=0，意味着最高位进位，数组需要扩容
                if (i == 0) {
                    int[] newDigits = new int[digits.length + 1];
                    System.arraycopy(digits, 0, newDigits, 1, digits.length);
                    newDigits[0] = 1;
                    return newDigits;
                }
            } else {
                digits[i] += 1;
                break;
            }
        }
        return digits;
    }

    public int[] solution2(int[] digits) {
        // 优化点1：如果最低位不是9，直接加1返回，避免走进循环，增加内存消耗
        if (digits[digits.length - 1] < 9) {
            digits[digits.length - 1] += 1;
            return digits;
        }
        // 优化点2：最低位是9，直接赋值为0，减少一次循环次数
        // 循环变量i从digits.length - 2开始，减少内存占用
        // 少了一次循环，让内存占用从击败84%变成击败91%
        digits[digits.length - 1] = 0;
        for (int i = digits.length - 2; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
                // 优化点3：进位判断放在循环外，但是只能减少判断次数，
                // 并不能减少内存消耗，反而会因为代码多了增加内存消耗
            } else {
                digits[i] += 1;
                break;
            }
        }
        // 优化点3：循环外进位判断，减少循环内判断次数，优化内存占用
        if (digits[0] == 0) {
            int[] newDigits = new int[digits.length + 1];
            System.arraycopy(digits, 0, newDigits, 1, digits.length);
            newDigits[0] = 1;
            return newDigits;
        }
        return digits;
    }
}
