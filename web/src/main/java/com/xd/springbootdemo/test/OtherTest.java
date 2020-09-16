package com.xd.springbootdemo.test;

import com.xd.springbootdemo.util.DateUtil;
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

    @Test
    public void testDate() {
        String date = DateUtil.getCurrentDate();
        long minTime = DateUtil.stringToDate(date + " 11:40:00", "yyyy-MM-dd HH:mm:ss").getTime();
        long maxTime = DateUtil.stringToDate(date + " 11:45:00", "yyyy-MM-dd HH:mm:ss").getTime();
        long curTime = System.currentTimeMillis();
        System.out.println(date);
        System.out.println(minTime);
        System.out.println(maxTime);
        System.out.println(curTime);

        if (!(minTime <= curTime && curTime <= maxTime)) {
            System.out.println("false");
        } else {
            System.out.println("true");
            return;
        }

        try {
            System.out.println("try");
        } finally {
            System.out.println("finally");
        }
    }

    @Test
    public void testToString() {
        List<tt> list = new ArrayList<>();
        tt t1 = new tt();
        t1.setI(1);
        t1.setJ("j1");
        list.add(t1);
        t1 = new tt();
        t1.setI(2);
        t1.setJ("j2");
        list.add(t1);
        System.out.println(list.toString());
    }

    private class tt {
        private int i;
        private String j;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public String getJ() {
            return j;
        }

        public void setJ(String j) {
            this.j = j;
        }

        @Override
        public String toString() {
            return String.format("tt{i=%d, j='%s'}", i, j);
        }
    }
}
