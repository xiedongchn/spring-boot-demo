package com.xd.springbootdemo.jvm;

/**
 * @author xd
 * Created on 2021/3/24
 */
public class JvmTest {

    /**
     * 测试YGC：
     * -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:gc.log
     * 新生代分配5MB，堆内存10MB，eden4MB，survivor0.5MB，大对象超过10MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void main(String[] args) {// main方法执行时入栈
        byte[] array1 = new byte[1024 * 1024];// 先在eden分配1MB的对象
        array1 = new byte[1024 * 1024];// 再分配一个1MB的对象
        array1 = new byte[1024 * 1024];// 再分配一个1MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[1024 * 1024 * 2];// 再分配一个2MB的对象，此时eden空间只有1MB空闲，内存不足，触发YGC
    }

}
