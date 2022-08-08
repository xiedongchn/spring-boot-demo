package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * 第一个错误的版本
 * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
 * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
 * 你可以通过调用bool isBadVersion(version)接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
 *
 * 示例
 * 输入：n = 5, bad = 4
 * 输出：4
 * 解释：
 * 调用 isBadVersion(3)-> false
 * 调用 isBadVersion(5)-> true 
 * 调用 isBadVersion(4)-> true
 * 所以，4 是第一个错误的版本。
 *
 * 个人理解
 * 该题更注重考察左右边界开闭的问题，即左右边界的值该如何保留
 * 例如右边界的收缩应该写成right = mid，而不是原生二分法的right = mid - 1，因为右边界可能是唯一解，如果丢弃则会找不到答案
 *
 * @author xd
 */
public class BinarySearch_278 {

    public int firstBadVersion(int n) {
        int left = 1, right = n;
        // 这个写法左右边界可以重合，重合时isBadVersion(left)必然是true，直接返回left即可
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 每次进来先判断左边界是不是，是即可返回
            if (isBadVersion(left)) {
                return left;
            } else if (isBadVersion(mid)) {
                // 如果中点满足，则收缩右边界，但是因为该题左右边界都是闭区间，所以不能写成right = mid - 1，可能会漏掉
                right = mid;
            } else {
                // 如果中点不满足，则收缩左边界
                left = mid + 1;
            }
        }
        // 走到这里时，left=right，区间收缩为一个点，即为答案
        return left;
    }

    // 该解法对比前一种解法，大多是情况下能减少对isBadVersion的调用
    // 假设当n是1000000万时，从第1个版本开始就是错误的，第1种解法由于有isBadVersion(left)的判断，可以在第一次循环就结束
    // 但是第二种解法需要一直收缩右边界知道左右边界重合才能找到答案
    // 如果isBadVersion复杂度较高，则第二种解法会更好
    public int firstBadVersion1(int n) {
        int left = 1, right = n;
        // 循环条件左右边界不可以重合，重合时重合时收缩为一个点，即为答案
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 每次判断中点，如果满足，则收缩右边界，但是因为该题左右边界都是闭区间，所以不能写成right = mid - 1，可能会漏掉
            if (isBadVersion(mid)) {
                right = mid;
            } else {
                // 如果中点不满足，则收缩左边界
                left = mid + 1;
            }
        }
        // 走到这里时，left=right，区间收缩为一个点，即为答案
        return left;
    }

    public boolean isBadVersion(int version) {
        return true;
    }

    @Test
    public void testSearch1() {
        System.out.println(firstBadVersion(9));
    }
}