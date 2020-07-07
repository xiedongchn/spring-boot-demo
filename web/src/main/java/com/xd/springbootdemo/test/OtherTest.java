package com.xd.springbootdemo.test;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class OtherTest {

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "2");
        System.out.println("2".equals(map.get("status")));

        BigDecimal a = new BigDecimal("123");
        System.out.println(a.toString());
        System.out.println(a.toEngineeringString());
        System.out.println(a.toPlainString());

    }

    @Test
    public void test1() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        list.add(map);
        map = new HashMap<>();
        map.put("2", "2");
        list.add(map);
        for (Map<String, String> o : list) {
            System.out.println(o.toString());
        }
    }

    @Test
    public void test2() {
        System.out.println("ea8180c2-a64d-4645-99b8-0210c71f980c_1".substring(0, "ea8180c2-a64d-4645-99b8-0210c71f980c_1".length()-1));
        System.out.println(new StringTokenizer("ea8180c2-a64d-4645-99b8-0210c71f980c_1", "_"));
    }
}
