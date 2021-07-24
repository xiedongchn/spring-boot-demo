package com.xd.springboot.product.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:+PrintHeapAtGC：打印GC前堆使用情况
 * -XX:TargetSurvivorRatio=80：动态年龄占比，默认50，即年龄1+年龄2+......+年龄n的占比达到50%，以上，则大于等于年龄n的对象直接进入老年代
 * -XX:+PrintGCTimeStamps：打印GC时间
 * -XX:+PrintGCDateStamps：打印GC日期时间
 * @author xd
 * Created on 2021/3/24
 */
public class JvmTest {

    public static void main(String[] args) {// main方法执行时入栈
        testJmapDump();
    }

    /**
     * todo
     * 场景：测试YGC，在idea中，会出现第三行代码就GC的情况，看到有学员说通过java -jar的方式能接近老师的结果，也就是只GC一次，参数：
     * -XX:NewSize=5242880 -XX:MaxNewSize=5242880 -XX:InitialHeapSize=10485760 -XX:MaxHeapSize=10485760 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ygc1.log
     * 新生代分配5MB，堆内存10MB，eden4MB，survivor5MB，大对象超过10MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void testYGC0() {
        byte[] array1 = new byte[1024 * 1024];// 先在eden分配1MB的对象
        array1 = new byte[1024 * 1024];// 再分配一个1MB的对象
        array1 = new byte[1024 * 1024];// 再分配一个1MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[1024 * 1024];// 再分配一个2MB的对象，此时eden空间只有1MB空闲，内存不足，触发YGC
    }

    /**
     * 场景：测试YGC，参数：
     * -XX:NewSize=524288000 -XX:MaxNewSize=524288000 -XX:InitialHeapSize=1048576000 -XX:MaxHeapSize=1048576000 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=1048576000 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ygc1.log
     * 新生代分配500MB，堆内存1000MB，eden400MB，survivor50MB，大对象超过1000MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void testYGC1() {//314572800//331350016
        byte[] array1 = new byte[1024 * 1024 * 100];// 先在eden分配100MB的对象
        array1 = new byte[1024 * 1024 * 100];// 再分配一个100MB的对象
        array1 = new byte[1024 * 1024 * 100];// 再分配一个100MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[1024 * 1024 * 200];// 再分配一个200MB的对象，此时eden空间只有100MB空闲，内存不足，触发YGC
    }

    /**
     * 场景：测试YGC 15次垃圾回收后,对象自动进入老年代,参数：
     * -XX:NewSize=524288000 -XX:MaxNewSize=524288000 -XX:InitialHeapSize=1048576000 -XX:MaxHeapSize=1048576000 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=1048576000 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ygc2.log
     * 新生代分配500MB，堆内存1000MB，eden400MB，survivor50MB，大对象超过1000MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void testYGC2() {
        byte[] array1 = new byte[1024 * 1024 * 40];// 先在eden分配256MB的对象
        for (int i = 0; i < 18; i++) {
            byte[] array2 = new byte[1024 * 1024 * 200];// 在eden分配200M左右的对象
            array2 = null;
        }
    }

    /**
     * todo
     * 场景：测试YGC survivor空间不足以容纳YGC后eden存活的对象时，可能出现的部分对象进入survivor，部分对象直接进入老年代,参数：
     * -XX:NewSize=524288000 -XX:MaxNewSize=524288000 -XX:InitialHeapSize=1048576000 -XX:MaxHeapSize=1048576000 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=1048576000 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ygc2.log
     * 新生代分配500MB，堆内存1000MB，eden400MB，survivor50MB，大对象超过1000MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void testYGC3() {
        byte[] array1 = new byte[1024 * 1024 * 40];// 先在eden分配256MB的对象
        for (int i = 0; i < 18; i++) {
            byte[] array2 = new byte[1024 * 1024 * 200];// 在eden分配200M左右的对象
            array2 = null;
        }
    }

    /**
     * 场景：测试大对象直接进入老年代,参数：
     * -XX:NewSize=524288000 -XX:MaxNewSize=524288000 -XX:InitialHeapSize=1048576000 -XX:MaxHeapSize=1048576000 -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=10485760 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:bigO1.log
     * 新生代分配500MB，堆内存1000MB，eden400MB，survivor50MB，大对象超过10MB直接进入老年代，年轻代使用 ParNew 垃圾回收器，老年代使用 CMS 垃圾回收器
     *
     * -XX:+PrintGCDetils：打印详细的 gc 日志
     * -XX:+PrintGCTimeStamps：这个参数可以打印出来每次 GC 发生的时间
     * -Xloggc:gc.log：这个参数可以设置将 gc 日志写入一个磁盘文件
     */
    public static void testBigObject1() {
        byte[] array1 = new byte[1024 * 1024 * 40];// 先在eden分配40MB的对象
    }

    /**
     * 场景：测试OldGC，参数：
     * -XX:NewSize=1048576000 -XX:MaxNewSize=1048576000 -XX:InitialHeapSize=2097152000 -XX:MaxHeapSize=2097152000 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=1048576000 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ogc1.log
     * 新生代1000M，堆大小2000M，eden800M，两个survivor各100M，对象15岁转老年代，1000M大对象直接进入老年代，使用ParNew+CMS
     */
    public static void testOGC1() {
        byte[] array1 = new byte[1024 * 1024 * 200];// 先在eden分配200MB的对象
        array1 = new byte[1024 * 1024 * 200];// 再分配一个200MB的对象
        array1 = new byte[1024 * 1024 * 200];// 再分配一个200MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[128 * 1024 * 100];// 再分配一个12800KB的对象
        // 再分配一个200MB的对象，此时eden空间只有800M-(200M*3+12800KB+其它对象)空闲，内存不足，触发YGC
        // ygc后，这个200M的数组对象进入eden，12800KB的数组和其它对象在from survivor中
        byte[] array3 = new byte[1024 * 1024 * 200];
    }

    /**
     * 场景：测试OldGC，参数：
     * -XX:NewSize=1048576000 -XX:MaxNewSize=1048576000 -XX:InitialHeapSize=2097152000 -XX:MaxHeapSize=2097152000 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=1048576000 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ogc2.log
     * 新生代1000M，堆大小2000M，eden800M，两个survivor各100M，对象15岁转老年代，1000M大对象直接进入老年代，使用ParNew+CMS
     */
    public static void testOGC2() {
        byte[] array1 = new byte[1024 * 1024 * 200];// 先在eden分配200MB的对象
        array1 = new byte[1024 * 1024 * 200];// 再分配一个200MB的对象
        array1 = new byte[1024 * 1024 * 200];// 再分配一个200MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[1024 * 1024 * 60];// 再分配一个12800KB的对象
        // 再分配一个200MB的对象，此时eden空间只有800M-(200M*3+12800KB+其它对象)空闲，内存不足，触发YGC
        // ygc后，这个200M的数组对象进入eden，12800KB的数组和其它对象在from survivor
        byte[] array3 = new byte[1024 * 1024 * 200];

        array3 = new byte[1024 * 1024 * 200];// 再分配一个200M的数组，此时eden剩余不足400M
        array3 = new byte[1024 * 1024 * 200];// 再分配一个200M的数组，此时eden剩余不足200M
        array3 = new byte[128 * 1024 * 100];// 再分配一个12800KB的数组，此时eden剩余100M多一些
        array3 = null;// 前面eden和survivor中的对象都变成垃圾对象

        // 再分配一个200MB的对象，此时eden空间只有800M-(200M*3+12800KB+其它对象)空闲，内存不足，触发YGC
        // 此时触发空间担保规则,老年代空间足够,直接YGC
        // from survivor此时由于存在array2和其它一些对象,占用超过50%,触发动态年龄判断
        // survivor中的对象全部进入老年代,这个200M的数组直接分配在eden中
        byte[] array4 = new byte[1024 * 1024 * 200];

        // 最终存活对象:array2和array4引用的对象
    }

    /**
     * todo
     * 047、高级工程师的硬核技能：JVM的Full GC日志应该怎么看？
     * 场景：测试OldGC 年轻代存活的对象太多放不下老年代了，此时触发 CMS 的 Full GC，参数：
     * -XX:NewSize=1048576000 -XX:MaxNewSize=1048576000 -XX:InitialHeapSize=2097152000 -XX:MaxHeapSize=2097152000 -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:PretenureSizeThreshold=1048576000 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:ogc1.log
     * 新生代1000M，堆大小2000M，eden800M，两个survivor各100M，对象15岁转老年代，1000M大对象直接进入老年代，使用ParNew+CMS
     */
    public static void testOGC3() {
        byte[] array1 = new byte[1024 * 1024 * 200];// 先在eden分配200MB的对象
        array1 = new byte[1024 * 1024 * 200];// 再分配一个200MB的对象
        array1 = new byte[1024 * 1024 * 200];// 再分配一个200MB的对象
        array1 = null;// 此时前三个对象成为垃圾对象

        byte[] array2 = new byte[128 * 1024 * 100];// 再分配一个12800KB的对象
        // 再分配一个200MB的对象，此时eden空间只有800M-(200M*3+12800KB+其它对象)空闲，内存不足，触发YGC
        // ygc后，这个200M的数组对象进入eden，12800KB的数组和其它对象在from survivor中
        byte[] array3 = new byte[1024 * 1024 * 200];
    }

    public static void testJmapDump() {
        List<Exception> exceptions = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            exceptions.add(new Exception("test"));
        }
        try {
            Thread.sleep(1000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
