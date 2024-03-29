package com.xd.springboot.product.jvm;

/**
 * 栈溢出测试
 *
 * @author xd
 */
public class StackOverflowTest {

    public static void main(String[] args) {
        test1(1);
    }

    /**
     * JVM参数：-Xss1m -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:NewSize=100m -XX:MaxNewSize=100m -XX:InitialHeapSize=200M -XX:MaxHeapSize=200M -XX:SurvivorRatio=8 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./ -Xloggc:MetaOOM1.log
     */
    public static void test1(int counter) {
        System.out.println("这是第" + counter + "次方法调用");
        test1(++counter);
    }
}
