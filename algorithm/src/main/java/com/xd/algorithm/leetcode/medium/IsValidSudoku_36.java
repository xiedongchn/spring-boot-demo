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

    @Test
    public void testSolution() {
        char[][] board = new char[][]
               {{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        System.out.println(solution1(board));
    }
}
