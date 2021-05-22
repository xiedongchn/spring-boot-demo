package com.xd.springbootdemo.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @author xd
 */
public class MetaspaceOomTest {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 测试元数据空间OOM
     * JVM参数：-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:NewSize=100m -XX:MaxNewSize=100m -XX:InitialHeapSize=200M -XX:MaxHeapSize=200M -XX:SurvivorRatio=8 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./ -Xloggc:MetaOOM1.log
     */
    public static void test1() {
        int counter = 0;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setUseCache(false);// 禁用缓存，每次都使用新的代理类
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            // 在执行run方法之前执行的逻辑
            if ("run".equals(method.getName())) {
                System.out.print("汽车启动之前，进行安全检查..");
            }
            return methodProxy.invokeSuper(o, objects);
        });
        while (true) {
            Car car = (Car) enhancer.create();
            car.run();
            counter++;
            System.out.println("创建了:" + counter + "个Car的子类");
        }
    }

    /**
     * 测试元数据空间OOM
     * JVM参数：-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:NewSize=100m -XX:MaxNewSize=100m -XX:InitialHeapSize=200M -XX:MaxHeapSize=200M -XX:SurvivorRatio=8 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:MetaOOM2.log
     *
     * 这个方法报错是：StackOverflowError，因为调用的是invoke方法而不是invokeSuper
     * cglib的net.sf.cglib.proxy.MethodProxy中提到了如果调用invoke并且传递了object, 则会导致反复拦截相当于死循环，从而导致Stackoverflow
     */
    public static void test2() {
        int counter = 0;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Car.class);
        enhancer.setUseCache(false);// 禁用缓存，每次都使用新的代理类
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            // 在执行run方法之前执行的逻辑
            if ("run".equals(method.getName())) {
                System.out.println("汽车启动之前，进行安全检查..");
            }
            // 这里使用invoke而不是invokeSuper
            return methodProxy.invoke(o, objects);
            // 与前一行代码报错一样
            // return method.invoke(o, objects);
        });
        while (true) {
            Car car = (Car) enhancer.create();
            car.run();
            counter++;
            System.out.println("创建了:" + counter + "个Car的子类");
        }
    }

}
