package com.xd.springbootdemo.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xd
 * Created on 2020/12/8
 */
public class Algorithm {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum1(nums, target)));
        System.out.println(Arrays.toString(twoSum2(nums, target)));
    }

    public static int[] twoSum1(int[] nums, int target) {
        // 解法一：双重循环遍历，由于一个元素不能重复使用，故外层循环从第一个元素开始遍历，内层循环从第二个元素开始遍历
        // 当碰上内外层循环对应下边的值之和为目标值时，返回内外层循环的下标值
        // 时间复杂度:最坏的情况是数组中任意两个元素都互相匹配一次,故时间复杂度是O(N^2)
        // 空间复杂度:算法计算的过程中占用内存空间的仅有i和j两个变量,故空间复杂度不随某一变量的增多而增加,故空间复杂度为O(1)
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static int[] twoSum2(int[] nums, int target) {
        // 解法二：通过哈希表降低时间复杂度,使用哈希表，可以将寻找 target - x 的时间复杂度降低到从 O(N)O(N) 降低到 O(1)O(1)。
        // 这样我们创建一个哈希表，对于每一个 x，我们首先查询哈希表中是否存在 target - x，然后将 x 插入到哈希表中，即可保证不会让 x 和自己匹配。
        // 当碰上内外层循环对应下边的值之和为目标值时，返回内外层循环的下标值
        // 时间复杂度:由于只需要遍历一次数组,故时间复杂度为O(1)
        // 空间复杂度:使用了哈希表,且哈希表占用的内存空间会随着数组元素的增多而变大,故空间复杂度为O(N)
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }


}
