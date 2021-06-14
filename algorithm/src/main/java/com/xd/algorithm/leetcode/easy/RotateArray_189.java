package com.xd.algorithm.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;

/**
 * 旋转数组nums[]，给定k，所有元素右移k位，在原数组上修改
 *
 * @author xd
 */
public class RotateArray_189 {

    public void rotate1(int[] nums, int k) {
        k %= nums.length;
        if (k <= 0 || nums.length == 1) {
            return;
        }

        int temp;
        int temp1;
        int maxIndex = nums.length - 1;
        // 右旋
        if (k <= nums.length / 2) {
            for (int i = 0; i < k; i++) {
                temp = nums[0];
                for (int j = 0; j < nums.length; j++) {
                    temp1 = 0;
                    if (j + 1 == nums.length) {
                        nums[0] = temp;
                    } else {
                        temp1 = nums[j + 1];
                        nums[j + 1] = temp;
                    }
                    temp = temp1;
                }
            }
            // 左旋
        } else {
            k = nums.length - k;
            for (int i = 0; i < k; i++) {
                temp = nums[maxIndex];
                for (int j = maxIndex; j >= 0; j--) {
                    temp1 = 0;
                    if (j == 0) {
                        nums[maxIndex] = temp;
                    } else {
                        temp1 = nums[j - 1];
                        nums[j - 1] = temp;
                    }
                    temp = temp1;
                }
            }
        }
    }

    public void rotate2(int[] nums, int k) {
        if (k <= 0 || k == nums.length || nums.length == 1 || k % nums.length == 0) {
            return;
        }

        k = k > nums.length ? k % nums.length : k;
        int[] temp;
        int maxIndex = nums.length - 1;
        // 右旋
        if (k <= nums.length / 2) {
            temp = new int[k];
            for (int i = nums.length - k; i < nums.length; i++) {
                temp[i + k - nums.length] = nums[i];
            }
            for (int i = maxIndex - k; i >= 0; i--) {
                nums[i + k] = nums[i];
            }
            for (int i = 0; i < k; i++) {
                nums[i] = temp[i];
            }
            // 左旋
        } else {
            k = nums.length - k;
            temp = new int[k];
            for (int i = 0; i < k; i++) {
                temp[i] = nums[i];
            }
            for (int i = 0; i < nums.length - k; i++) {
                nums[i] = nums[i + k];
            }
            for (int i = nums.length - k; i < nums.length; i++) {
                nums[i] = temp[i + k - nums.length];
            }
        }
    }

    public void rotate3(int[] nums, int k) {
        if (k <= 0 || k == nums.length || nums.length == 1 || k % nums.length == 0) {
            return;
        }

        k = k % nums.length;
        int[] temp = new int[nums.length];
        int maxIndex = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (i + k <= maxIndex) {
                temp[i + k] = nums[i];
            } else {
                temp[(i + k) % nums.length] = nums[i];
            }
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp[i];
        }
    }

    public void rotate4(int[] nums, int k) {
        k = k % nums.length;
        if (k <= 0 || nums.length == 1) {
            return;
        }

        int[] temp = new int[nums.length];
        int maxIndex = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (i + k <= maxIndex) {
                temp[i + k] = nums[i];
            } else {
                temp[(i + k) % nums.length] = nums[i];
            }
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp[i];
        }
    }

    /**
     * 这个解法有问题，当k =
     * @param nums
     * @param k
     */
    public void rotate5(int[] nums, int k) {
        k %= nums.length;
        if (k <= 0 || nums.length == 1) {
            return;
        }

        int temp = nums[0];
        for (int i = k; i < nums.length + k; i++) {
            int temp1 = nums[i % nums.length];
            nums[(i + k) % nums.length] = temp;
            temp = temp1;
        }
        nums[k] = temp;
    }

    @Test
    public void testRotate1() {
        int[] array = new int[]{1,2,3,4,5,6,7};
        rotate5(array, 16);
        System.out.println(Arrays.toString(array));
        array = new int[]{1,2,3};
        rotate5(array, 8);
        System.out.println(Arrays.toString(array));
    }
}
