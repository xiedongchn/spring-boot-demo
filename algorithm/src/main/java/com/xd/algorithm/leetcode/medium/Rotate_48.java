package com.xd.algorithm.leetcode.medium;

import org.junit.Test;

import java.util.Arrays;

/**
 * 旋转图像
 *
 * @author xd
 * Created on 2021/6/21
 */
public class Rotate_48 {

    /**
     * 第一种解法，先上下交换，再对角线交换
     *
     * @param matrix 矩阵
     */
    public void solution(int[][] matrix) {
        int length = matrix.length;
        // 先上下交换
        // 比如4 * 4的矩阵
        // 第一行和第四行交换，第二行和第三行交换
        // 因此，只需要循环时，只要遍历前两行即可
        for (int i = 0; i < length / 2; i++) {
            int[] temp = matrix[i];
            matrix[i] = matrix[length - 1 - i];
            matrix[length - 1 - i] = temp;
        }
        // 再对角线交换
        for (int i = 0; i < length; i++) {
            // 当i = j时，意味着matrix[i][j]是对角线上的元素
            // 当j = i + 1，意思是取对角线右上的元素，然后将右上的元素和左下的进行交换
            for (int j = i + 1; j < length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    @Test
    public void test() {
        int[][] matrix = new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}};
        solution(matrix);
        System.out.println(Arrays.deepToString(matrix));
    }
}
