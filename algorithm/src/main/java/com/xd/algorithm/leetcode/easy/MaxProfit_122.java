package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

/**
 * 买卖股票
 *
 * @author xd
 */
public class MaxProfit_122 {

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int sum = 0;
        // 标记买入的位置,为-1时代表没买入,大于1时代表已买入,且值就是买入的下标
        int buyIndex = -1;
        // 限制最大能达到的下标,因为买入或者卖出都是要和下一天比较的,避免下标越界
        int maxIndex = prices.length - 1;
        // 这里从下标0开始遍历,依次和下一个数相比较,小于买入,大于卖出
        for (int i = 0; i < prices.length; i++) {
            // 卖出的两种情况,首先满足买入条件(buyIndex != -1):
            // 1.当天价格大于下一天
            // 2.已经到最后一天
            if (buyIndex != -1 && (i < maxIndex && prices[i] > prices[i + 1] || i == maxIndex)) {
                buyIndex = -1;
                sum += prices[i];
            } else if (buyIndex == -1 && i < maxIndex && prices[i] < prices[i + 1]) {
                buyIndex = i;
                sum -= prices[buyIndex];
            }
        }
        return sum;
    }

    @Test
    public void testMaxProfit() {
        int[] array = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(array));
        array = new int[]{1, 2, 3, 4, 5};
        System.out.println(maxProfit(array));
    }
}
