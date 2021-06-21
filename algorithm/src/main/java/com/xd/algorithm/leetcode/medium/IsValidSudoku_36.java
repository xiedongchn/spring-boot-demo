package com.xd.algorithm.leetcode.medium;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 请你判断一个 9x9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
 * <p>
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 *
 * @author xd
 * Created on 2021/6/15
 */
public class IsValidSudoku_36 {

    public boolean solution1(char[][] board) {
        // 横数组
        Map<Character, Character> r = new HashMap<>();
        // 纵数组
        Map<Character, Character> c = new HashMap<>();
        // 第一个九宫格
        Map<Character, Character> b1 = new HashMap<>();
        // 第二个九宫格
        Map<Character, Character> b2 = new HashMap<>();
        // 第三个九宫格
        Map<Character, Character> b3 = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            r.clear();
            c.clear();
            if (i % 3 == 0) {
                b1.clear();
                b2.clear();
                b3.clear();
            }
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    if (r.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("r" + i + ":" + j);
                        return false;
                    }
                    if (j < 3 && b1.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("b2:" + i + ":" + j);
                        return false;
                    }
                    if (j >= 3 && j < 6 && b2.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("b2:" + i + ":" + j);
                        return false;
                    }
                    if (j >= 6 && j < 9 && b3.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("b3:" + i + ":" + j);
                        return false;
                    }
                }
                if (board[j][i] != '.') {
                    if (c.putIfAbsent(board[j][i], board[j][i]) != null) {
                        System.out.println("c:" + j + ":" + i);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean solution2(char[][] board) {
        // 横数组
        Map<Character, Character> r = new HashMap<>();
        // 纵数组
        Map<Character, Character> c = new HashMap<>();
        // 第一个九宫格
        Map<Character, Character> b1 = new HashMap<>();
        // 第二个九宫格
        Map<Character, Character> b2 = new HashMap<>();
        // 第三个九宫格
        Map<Character, Character> b3 = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            r.clear();
            c.clear();
            if (i % 3 == 0) {
                b1.clear();
                b2.clear();
                b3.clear();
            }
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != '.') {
                    if (r.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("r" + i + ":" + j);
                        return false;
                    }
                    if (j < 3 && b1.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("b2:" + i + ":" + j);
                        return false;
                    } else if (j >= 3 && j < 6 && b2.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("b2:" + i + ":" + j);
                        return false;
                    } else if (j >= 6 && j < 9 && b3.putIfAbsent(board[i][j], board[i][j]) != null) {
                        System.out.println("b3:" + i + ":" + j);
                        return false;
                    }
                }
                if (board[j][i] != '.') {
                    if (c.putIfAbsent(board[j][i], board[j][i]) != null) {
                        System.out.println("c:" + j + ":" + i);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 对位运算不熟悉
     * 0 0 0 0 0 0 1 0      2
     * 0 0 0 0 0 1 0 0      4
     * -------------------- | 按位或运算
     * 0 0 0 0 0 1 1 0      6
     *
     * 此时如果新的值为8
     * 0 0 0 0 0 1 1 0      6
     * 0 0 0 0 1 0 0 0      8
     * -------------------- & 按位与运算
     * 0 0 0 0 0 0 0 0      0
     *
     * 此时如果新的值是重复的4
     * 0 0 0 0 0 1 1 0      6
     * 0 0 0 0 0 1 0 0      4
     * -------------------- & 按位与运算
     * 0 0 0 0 0 1 0 0      4 值大于0
     */
    public static boolean solution3(char[][] board) {
        int[] line = new int[9];
        int[] column = new int[9];
        int[] cell = new int[9];
        int shift;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //如果还没有填数字，直接跳过
                if (board[i][j] == '.')
                    continue;
                //将1按位左移相应位数 得到的数即为2的倍数 2、4、8、16的值
                shift = 1 << (board[i][j] - '0'); //使用 -'0' 实现char转int型,然后让1左移对应的位数
                int k = (i / 3) * 3 + j / 3;
                //如果对应的位置只要有一个大于0，说明有冲突，直接返回false
                //如果新的数字是不重复的，按位与后值应该都为0
                if ((column[i] & shift) > 0 || (line[j] & shift) > 0 || (cell[k] & shift) > 0)
                    return false;
                column[i] |= shift;  //按位或，同一列未重复的数字按位或
                line[j] |= shift;
                cell[k] |= shift;
            }
        }
        return true;
    }

    public boolean solution4(char[][] board) {
        int[] mark = new int[9];
        int shift;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                // 如果还没有填数字，直接跳过
                if (board[x][y] == '.') {
                    continue;
                }
                shift = 1 << (board[x][y] - '0');
                if ((mark[x] & shift) != 0) {
                    return false;
                }
                mark[x] |= shift;
                shift = shift << 9;
                if ((mark[y] & shift) != 0) {
                    return false;
                }
                mark[y] |= shift;
                shift = shift << 9;
                int z = (x / 3) * 3 + y / 3;
                if ((mark[z] & shift) != 0) {
                    return false;
                }
                mark[z] |= shift;
            }
        }
        return true;
    }

    @Test
    public void testSolution() {
        int i = '5' - '0';
        int j = 1 << i;
        System.out.println(i);
        System.out.println(j);
    }
}
