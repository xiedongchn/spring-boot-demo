package com.xd.springbootdemo.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
}
