package com.xd.springbootdemo.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@TestComponent//Mark a bean as the component should be excluded from Spring Boot's component scanning.
@SpringBootTest
public class ThreadTest {

    @Test
    public void testHandleDataWithThreadPool() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("---------start:" + i + "------------");
            handleDataWithThreadPool(getList(i), false, false);
            System.out.println("-----------end:" + i + "------------");
        }
    }

    /**
     * 线程池处理数据方法
     *
     * @param data          待处理的数据
     * @param ceilOrFloor   数据集分片方式，要处理的数据大于线程数量时，页大小等于数据数量除以线程数得到的小数是向上取整(true)还是向下取整(false)
     * @param startTogether 为true时构造完所有线程后通过invokeAll一次调用，否则每构造一个线程就立刻通过submit启动，前者不需要初始化CountDownLatch对象
     */
    public void handleDataWithThreadPool(List<String> data, boolean ceilOrFloor, boolean startTogether) {
        int threadNum = 4;
        int perSize;
        if (ceilOrFloor) {
            //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向上取整
            perSize = data.size() < threadNum ? data.size() : (int) Math.ceil(Double.parseDouble(data.size() + "") / Double.parseDouble(threadNum + ""));
        } else {
            //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向下取整
            perSize = data.size() < threadNum ? data.size() : (int) Math.floor(Double.parseDouble(data.size() + "") / Double.parseDouble(threadNum + ""));
        }
        //要处理的数据小于线程数量时，默认使用一个线程
        threadNum = data.size() < threadNum ? 1 : ceilOrFloor ? (int) Math.ceil(Double.parseDouble(data.size() + "") / Double.parseDouble(perSize + "")) : threadNum;
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(threadNum));
        final List<String> resultList = Collections.synchronizedList(new ArrayList<>());
        final List<Future<List<String>>> futureList = Collections.synchronizedList(new ArrayList<>());
        final List<Callable<List<String>>> callableList = new ArrayList<>(threadNum);
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * perSize;
            if (ceilOrFloor) {
                if (end > data.size()) {//计算出来的下标值超出了列表大小
                    end = data.size();
                    // threadNum = i重置循环条件，使下一次循环不再执行，这行代码在ceilOrFloor为true时，会出问题，导致线程池占用的资源不会释放，
                    // 可以让data.size=5，threadNum=4时测试一下
                    //threadNum = i;
                }
            } else {
                //最后一次循环了数据还没处理完，则把剩下的数据全部丢到最后一次循环中处理
                if (i + 1 == threadNum && end < data.size()) {
                    end = data.size();
                }
            }

            final int k = i;
            final int start = i * perSize;
            final int end1 = end;
            final List<String> subList = data.subList(start, end);
            if (!startTogether) {//每构造一个线程就通过submit启动
                Future<List<String>> future = executorService.submit(() -> {
                    long st = System.currentTimeMillis();
                    List<String> result = new ArrayList<>();
                    for (String s : subList) {
                        result.add(s + "k");
                    }

                    //计数器减一
                    countDownLatch.countDown();

                    long et = System.currentTimeMillis();
                    System.out.println("线程:" + k + ",size:" + subList.size() + ",s-e:" + (start + 1) + "-" + end1 + ",et-st:" + (et - st) + ",st:" + st + ",et:" + et);
                    return result;
                });
                /* 调用addAll会导致线程变成同步执行的
                try {
                    if (future.get() != null && !future.get().isEmpty()) {
                        resultList.addAll(future.get());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                // 把future添加到集合中，对结果的处理放在for循环之外，可避免线程同步执行问题
                futureList.add(future);
            } else {//构造完所有线程后添加到callables，在循环外一次启动
                Callable<List<String>> callable = () -> {
                    long st = System.currentTimeMillis();
                    List<String> result = new ArrayList<>();
                    for (String s : subList) {
                        result.add(s + "k");
                    }

                    long et = System.currentTimeMillis();
                    System.out.println("线程:" + k + ",size:" + subList.size() + ",s-e:" + (start + 1) + "-" + end1 + ",et-st:" + (et - st) + ",st:" + st + ",et:" + et);
                    return result;
                };
                callableList.add(callable);
            }
        }

        try {
            if (!startTogether) {
                countDownLatch.await();
            } else {
                futureList.addAll(executorService.invokeAll(callableList));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //关闭线程池，释放资源
        executorService.shutdown();

        for (Future<List<String>> future : futureList) {
            try {
                List<String> result = future.get();
                if (result != null && !result.isEmpty()) {
                    resultList.addAll(future.get());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("futureSize:" + futureList.size() + ",resultSize:" + resultList.size());
    }

    @Test
    public void testShutdownNow() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i++) {
            final int j = i;
            try {
                executorService.submit(() -> {
                    System.out.println(j + ":" + System.currentTimeMillis());
                });
                Thread.sleep((new Random().nextInt(1000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Runnable> task = executorService.shutdownNow();
            System.out.println(j + ":" + System.currentTimeMillis());
        }
    }

    @Test
    public void testListSplit() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {//测试线程数大于集合数据数量的情况
            splitList(getList(random.nextInt(20)), random.nextInt(100) + 20);
            System.out.println("***************************************");
        }

        System.out.println("\n\n\n");

        for (int i = 0; i < 10; i++) {//测试线程数小于集合数据数量的情况
            splitList(getList(random.nextInt(1000) + 100), random.nextInt(100));
            System.out.println("***************************************");
        }
    }

    /**
     * 按照线程数均匀分配List中的元素，并打印出来
     *
     * @param data      数据集合
     * @param threadNum 线程数量
     */
    public void splitList(List<String> data, int threadNum) {
        //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向上取整
        int perSize = data.size() < threadNum ? data.size() : (int) Math.ceil(Double.parseDouble(data.size() + "") / Double.parseDouble(threadNum + ""));
        //要处理的数据小于线程数量时，默认使用一个线程
        threadNum = data.size() < threadNum ? 1 : threadNum;

        System.out.println("length:" + data.size() + ",perSize:" + perSize + ",threadNum:" + threadNum);
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * perSize;
            if (end > data.size()) {//计算出来的下标值超出了列表大小
                end = data.size();
                threadNum = i;//重置循环条件，使下一次循环不再执行
            }

            List<String> subList = data.subList(i * perSize, end);

            StringBuilder builder = new StringBuilder("Round:").append(i).append(",start:").append(i * perSize).append(",end:").append(end).append("----subList:");
            for (String s : subList) {
                builder.append(s).append(",");
            }
            System.out.println(builder.toString());
        }
    }

    /**
     * 模拟生成数据
     *
     * @param size 数据量
     * @return 数据集
     */
    public List<String> getList(int size) {
        List<String> list = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            list.add("" + i);
        }
        return list;
    }
}
