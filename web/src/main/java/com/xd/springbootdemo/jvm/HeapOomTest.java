package com.xd.springbootdemo.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出测试
 *
 * @author xd
 */
public class HeapOomTest {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 设置堆总大小只有10M，让JVM尽快的溢出
     * JVM参数：-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:InitialHeapSize=10M -XX:MaxHeapSize=10M -XX:SurvivorRatio=8 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:HeapOOM1.log
     */
    public static void test1() {
        int counter = 1;
        List<Object> objList = new ArrayList<>();
        while (true) {
            System.out.println("添加第" + counter + "个对象");
            objList.add(new Object());
            counter += 1;
        }
    }
}
