package com.xd.springbootdemo.test;

import com.xd.springbootdemo.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void test3() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse("2020-09-23");
        Date date2 = format.parse("2020-09-24");
        System.out.println(date1.before(date2));
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
        List<T> list = new ArrayList<>();
        T t1 = new T();
        t1.setI(1);
        t1.setJ("j1");
        list.add(t1);
        t1 = new T();
        t1.setI(2);
        t1.setJ("j2");
        list.add(t1);
        System.out.println(list.toString());
    }

    @Test
    public void testCompareDateStr() {
        System.out.println(DateUtil.compareStrDate("2020-10-03", "2020-10-02"));
    }

    @Test
    public void testSpringBeanUtils() {
        List<T> list = new ArrayList<>();
        T t1 = new T(1, "j1", new T.InnerT(2, "j2"));
        list.add(t1);

        List<T> list1 = new ArrayList<>();
        for (T t : list) {
            T newT = new T();
            BeanUtils.copyProperties(t, newT);
            list1.add(newT);
        }

        List<T> list2 = new ArrayList<>();
        for (T t : list) {
            T newT = new T(3, "j3", new T.InnerT(4, "j4"));
            BeanUtils.copyProperties(t, newT);
            list2.add(newT);
        }

        System.out.println(list.toString());
        System.out.println(list1.toString());
        System.out.println(list2.toString());

        list1.get(0).setI(5);
        list1.get(0).setJ("j6");
        list1.get(0).setInnerT(null);

        list2.get(0).setI(7);
        list2.get(0).setJ("j8");
        list2.get(0).setInnerT(new T.InnerT(9, "j10"));

        System.out.println(list.toString());
        System.out.println(list1.toString());
        System.out.println(list2.toString());
        System.out.println(StandardCharsets.UTF_8.name());
    }

    private static class T {
        private int i;
        private String j;
        private InnerT innerT;

        public T() {
        }

        public T(int i, String j, InnerT innerT) {
            this.i = i;
            this.j = j;
            this.innerT = innerT;
        }

        public static class InnerT {
            private int i;
            private String j;

            public InnerT(int i, String j) {
                this.i = i;
                this.j = j;
            }

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
        }

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

        public InnerT getInnerT() {
            return innerT;
        }

        public void setInnerT(InnerT innerT) {
            this.innerT = innerT;
        }

        @Override
        public String toString() {
            return String.format("T{i=%d, j='%s', innerT=%s}", i, j, innerT);
        }
    }
}
