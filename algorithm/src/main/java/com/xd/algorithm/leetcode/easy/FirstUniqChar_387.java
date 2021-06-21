package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xd
 * Created on 2021/6/21
 */
public class FirstUniqChar_387 {

    public int solution1(String s) {
        char[] c = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < c.length; i++) {
            // 存在表明重复
            if (map.get(c[i]) != null) {
                // 重复了就置为-1
                map.put(c[i], -1);
            } else {
                map.put(c[i], i);
            }
        }
        int min = -1;
        for (int i : map.values()) {
            if (min == -1 && i > -1) {
                min = i;
            } else if (i != -1 && i < min) {
                min = i;
            }
        }
        return min;
    }

    public int solution2(String s) {
        char[] c = s.toCharArray();
        // 创建一个26位的数组，分别存储a~z的下标
        int[] k = new int[26];
        for (int i = 0; i < c.length; i++) {
            // 减去字符'a'，得到字母对应的整型数值,a->0，z->25
            int index = c[i] - 'a';
            // 第一次出现
            if (k[index] == 0) {
                // 这里让下标+1，因为数组默认值是0，以作区分
                k[index] = i + 1;
            } else if (k[index] > 0) {
                // 大于0，意味着第二次出现，赋值-1，表明重复
                k[index] = -1;
            }
        }
        // 题干要求找不到则返回-1
        int min = -1;
        // 遍历下标数组，取出最小值
        for (int i : k) {
            // 下标需要大于0，因为前面记录下标为'i + 1'
            if (i > 0) {
                // min为-1，直接赋值为i
                if (min == -1) {
                    min = i;
                } else if (i < min) {
                    // i < min，表明存在更小的下标值，赋值给min
                    min = i;
                }
            }
        }
        // min大0时，表明找到一个有效的下标，因为前面'i + 1'，这里要减去1，min小于0则返回-1
        return min > 0 ? min - 1 : -1;
    }

    public int solution3(String s) {
        int[] k = new int[26];
        char[] c = s.toCharArray();
        // 先统计每个字符出现的次数
        for (int i = 0; i < s.length(); i++)
            k[c[i] - 'a']++;
        // 然后在遍历字符串s中的字符，如果出现次数是1就直接返回
        // 相比solution2，减少了数组k的循环次数，缺点是要重复计算c[i] - 'a'
        for (int i = 0; i < s.length(); i++)
            if (k[c[i] - 'a'] == 1)
                return i;
        return -1;
    }

    @Test
    public void test() {
        System.out.println(solution2("dabbcb"));
        System.out.println(solution2("leetcode"));
    }
}
