package com.xd.springbootdemo.jvm;

/**
 * @author xd
 * Created on 2021/3/24
 */
public class JvmTest {

    public static void main(String[] args) {// main方法执行时入栈
        testYGC2();
    }

    /**
     * 测试YGC，参数：
     * -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ygc1.log
     * 新生代分配5MB，堆内存10MB，eden4MB，survivor0.5MB，大对象超过10MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void testYGC1() {
        byte[] array1 = new byte[1024 * 1024];// 先在eden分配1MB的对象
        array1 = new byte[1024 * 1024];// 再分配一个1MB的对象
        array1 = new byte[1024 * 1024];// 再分配一个1MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[1024 * 1024 * 2];// 再分配一个2MB的对象，此时eden空间只有1MB空闲，内存不足，触发YGC
    }

    /**
     * 测试YGC 15次垃圾回收后,对象自动进入老年代,参数：
     * -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=6 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ygc2.log
     * 新生代分配5MB，堆内存10MB，eden4MB，survivor0.5MB，大对象超过10MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void testYGC2() {
        byte[] array1 = new byte[1024 * 256];// 先在eden分配256KB的对象
        for (int i = 0; i < 18; i++) {
            byte[] array2 = new byte[1024 * 1024 * 2];// 在eden分配3M左右的对象
            array2 = null;
        }
    }

    /**
     * 测试OldGC，参数：
     * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ogc1.log
     * 新生代10M，堆大小20M，eden8M，两个survivor各1M，对象15岁转老年代，10M大对象直接进入老年代，使用ParNew+CMS
     */
    public static void testOGC1() {
        byte[] array1 = new byte[1024 * 1024 * 2];// 先在eden分配2MB的对象
        array1 = new byte[1024 * 1024 * 2];// 再分配一个2MB的对象
        array1 = new byte[1024 * 1024 * 2];// 再分配一个2MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[128 * 1024];// 再分配一个128KB的对象
        // 再分配一个2MB的对象，此时eden空间只有8M-(2M*3+128KB+其它对象)空闲，内存不足，触发YGC
        // ygc后，这个2M的数组对象进入eden，128KB的数组和其它对象在from survivor钟
        byte[] array3 = new byte[1024 * 1024 * 2];
    }

    /**
     * 测试OldGC，参数：
     * -XX:NewSize=10485760 -XX:MaxNewSize=10485760 -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ogc2.log
     * 新生代10M，堆大小20M，eden8M，两个survivor各1M，对象15岁转老年代，10M大对象直接进入老年代，使用ParNew+CMS
     */
    public static void testOGC2() {
        byte[] array1 = new byte[1024 * 1024 * 2];// 先在eden分配2MB的对象
        array1 = new byte[1024 * 1024 * 2];// 再分配一个2MB的对象
        array1 = new byte[1024 * 1024 * 2];// 再分配一个2MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[128 * 1024];// 再分配一个128KB的对象
        // 再分配一个2MB的对象，此时eden空间只有8M-(2M*3+128KB+其它对象)空闲，内存不足，触发YGC
        // ygc后，这个2M的数组对象进入eden，128KB的数组和其它对象在from survivor
        byte[] array3 = new byte[1024 * 1024 * 2];

        array3 = new byte[1024 * 1024 * 2];// 再分配一个2M的数组，此时eden剩余不足4M
        array3 = new byte[1024 * 1024 * 2];// 再分配一个2M的数组，此时eden剩余不足2M
        array3 = new byte[128 * 1024];// 再分配一个128KB的数组，此时eden剩余1M多一些
        array3 = null;// 前面eden和survivor中的对象都变成垃圾对象

        // 再分配一个2MB的对象，此时eden空间只有8M-(2M*3+128KB+其它对象)空闲，内存不足，触发YGC
        // 此时触发空间担保规则,老年代空间足够,直接YGC
        // from survivor此时由于存在array2和其它一些对象,占用超过50%,触发动态年龄判断
        // survivor中的对象全部进入老年代,这个2M的数组直接分配在eden中
        byte[] array4 = new byte[1024 * 1024 * 2];

        // 最终存活对象:array2和array4引用的对象
    }

}
