package com.xd.springboot.common.util;

/**
 * @author xd
 * Created on 2021/4/7
 */
public class StringUtils {

    public static String convertHqlToSql(String target) {
        if (target == null || target.length() == 0) {
            return null;
        }
        String temp = target.replace("id.", "");
        char[] array = temp.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            if (c >= 'A' && c <= 'Z') {
                c += 32;
                if (i > 0 && array[i - 1] >= 'a' && array[i - 1] <= 'z') {
                    result.append('_');
                }
            }
            result.append(c);
        }
        return result.toString();
    }

    public static int isUpperCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return 1;
        } else if (c >= 'a' && c <= 'z') {
            return 0;
        }
        return 2;
    }

    public static Character changeCase(char c) {
        //如果输入的是大写，+32即可得到小写
        if (c >= 'A' && c <= 'Z') {
            c += 32;
        } else if (c >= 'a' && c <= 'z') {
            //如果输入的是小写，-32即可得大小写
            c -= 32;
        } else {
            return null;
        }
        return c;
    }

    public static void main(String[] args) {
        System.out.println(convertHqlToSql(""));
    }
}
