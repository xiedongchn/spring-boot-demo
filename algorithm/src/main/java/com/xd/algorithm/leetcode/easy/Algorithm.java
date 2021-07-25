package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xd
 * Created on 2020/12/8
 */
public class Algorithm {

    @Test
    public void testTwoSum() {
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

    @Test
    public void testReverse() {
        System.out.println(reverse1(2147483641));
        System.out.println(reverse1(-2147483641));
        System.out.println(reverse2(2147483641));
        System.out.println(reverse2(-2147483641));
    }

    public static int reverse1(int x) {
        // 解法一：直接将数字转成字符串，然后将字符串反转，不过要注意反转后的数字整型溢出问题，故用长整型接收转换后的字符串
        char[] c = String.valueOf(x).toCharArray();
        StringBuilder c1 = new StringBuilder();
        for (int i = c.length - 1; i >= 0; i--) {
            if ('-' == c[i]) {// 如果是负数，反转后减号必然在最后一位，把减号插入到字符串最前面，然后跳出循环
                c1.insert(0, '-');
                break;
            }
            c1.append(c[i]);
        }
        long x1 = Long.parseLong(c1.toString());
        return (int) (x1 > Integer.MAX_VALUE || x1 < Integer.MIN_VALUE ? 0 : x1);
    }

    public static int reverse2(int x) {
        // 解法二：数学法，具体做法是将原整数每次按10取模，即可得到最后的一位数，同时将原数除以10，然后将最后一位数乘以10，加上下一位数，
        // 以此类推，可把数该整数完全翻转过来，并且顺便解决了原数末尾是0的问题，但是要注意，循环处理到最后一位数时，乘以10之前要判断是否大于
        // Integer.MAX_VALUE / 10或者小于Integer.MIN_VALUE / 10，如果满足这两个条件，那么不用处理最后一位数，因为乘以10之后就溢出了。
        // 如果恰好等于Integer.MAX_VALUE / 10或者等于Integer.MIN_VALUE / 10，那么对于满足前者时，要判断最后一位数是否大于7，如果大于
        // 7，那么乘以10再加上最后一位数，必然大于Integer.MAX_VALUE，当满足后者时，要判断最后一位数是否小于-8，如果小于-8，那么乘以10再
        // 加上最后一位数，必然小于Integer.MIN_VALUE
        // 注意：负数按10取模得到的也是负数
        // 举例如下：
        // -12345 % 10 = -5，-12345 / 10 = -1234，0 * 10 + (-5) = -5
        // -1234 % 10 = -4，-1234 / 10 = -123，-5 * 10 + (-4) = -54
        // -123 % 10 = -3，-123 / 10 = -12，-54 * 10 + (-3) = -543
        // -12 % 10 = -2，-12 / 10 = -1，-543 * 10 + (-2) = -5432
        // -1 % 10 = -1，-1 / 10 = 0，-5432 * 10 + (-1) = -54321
        // 时间复杂度：时间复杂度随着x位数的增加而增加，故O(log(x))或O(log10(x))
        // 空间复杂度：O(1)
        int res = 0;
        int max = Integer.MAX_VALUE / 10;
        int min = Integer.MIN_VALUE / 10;
        while (x != 0) {
            int last = x % 10;
            x /= 10;
            if (res > max || res == max && last > 7) {
                return 0;
            }
            if (res < min || res == min && last < -8) {
                return 0;
            }
            res = res * 10 + last;
        }
        return res;
    }

    @Test
    public void testIsPalindrome() {
        System.out.println(isPalindrome1(2147483641));
        System.out.println(isPalindrome2(1239321));
        System.out.println(isPalindrome1(-2147483641));
    }

    public static boolean isPalindrome1(int x) {
        // 解法一：判断回文数，首先小于0的直接返回false，等于0返回true，大于0但是末位是0的也返回false；
        // 其余大于0的数，反转一下，判断是否等于原数，等于则是回文数，否则反之
        return x >= 0 && (x == 0 || (x % 10 != 0 && x == reverse2(x)));
    }

    public static boolean isPalindrome2(int x) {
        // 解法二：大体思路于解法一是一致的，只是循环的时候循环次数少了一半，因为x > y这个条件会造成数字反转到一半的时候跳出循环，如下：
        // 1234321
        // 123432:1
        // 12343:12
        // 1234:123
        // 123:1234，在这里就跳出循环了
        if (x == 0) return true;
        if (x < 0 || x % 10 == 0) return false;
        int y = 0;
        while (x > y) {
            y = y * 10 + x % 10;
            x /= 10;
        }
        // 在x的位数是双数时，总是x == y，为单数时，总是x == y / 10
        return x == y || x == y / 10;
    }

    @Test
    public void testLongestCommonPrefix1() {
        System.out.println(longestCommonPrefix(new String[]{"d", "r", "c"}));
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println(longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
        System.out.println(longestCommonPrefix(new String[]{"ab", "a"}));
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0 || strs[0] == null || strs[0].length() == 0) {
            return "";
        }

        String res = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if (strs[i] == null || strs[i].length() == 0) {
                return "";
            }
            int minLength = Math.min(res.length(), strs[i].length());
            for (int j = 0; j < minLength; j++) {
                if (res.charAt(j) != strs[i].charAt(j)) {// 遇到不等的字符时，直接截断
                    if (j == 0) {// 第一个字符就不相等直接返回
                        return "";
                    }
                    res = res.substring(0, j);
                    break;
                } else if (j == minLength - 1 && res.charAt(j) == strs[i].charAt(j)) {// 相等的字符，没有到最后一个字符都不需要处理，当是最后一个字符时，进行阶段
                    res = res.substring(0, j + 1);
                    break;
                }
            }
        }
        return res;
    }

}
