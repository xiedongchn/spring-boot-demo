package com.xd.springbootdemo.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@TestComponent//Mark a bean as the component should be excluded from Spring Boot's component scanning.
@SpringBootTest
public class ThreadTest {

    public void executorServiceTest3(int size) throws InterruptedException {
        int threadNum = 4;
        List<String> data = getList(size);
        //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向上取整
        int perSize = data.size() < threadNum ? data.size() : (int) Math.floor(Double.parseDouble(data.size() + "") / Double.parseDouble(threadNum + ""));
        //要处理的数据小于线程数量时，默认使用一个线程
        threadNum = data.size() < threadNum ? 1 : threadNum;
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(threadNum));
        final List<String> resultList = Collections.synchronizedList(new ArrayList<>());
        final List<Future<List<String>>> futureList = Collections.synchronizedList(new ArrayList<>());
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * perSize;
            //最后一次循环了数据还没处理完，则把剩下的数据全部丢到最后一次循环中处理
            if (i + 1 == threadNum && end < data.size()) {
                end = data.size();
            }

            final int k = i;
            final int start = i * perSize;
            final int end1 = end;
            final List<String> subList = data.subList(start, end);
            Future<List<String>> future = executorService.submit(() -> {
                List<String> result = new ArrayList<>();
                try {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    System.out.println("当前线程" + k + "处理的数据量为:" + subList.size() + ",startTime:" + format.format(System.currentTimeMillis()) + ",s-e:" + (start+1) + "-" + end1);
                    for (String s : subList) {
                        result.add(s + "k");
                    }
                    System.out.println("当前线程" + k + "处理的数据量为:" + subList.size() + ",endTime:" + format.format(System.currentTimeMillis()) + ",s-e:" + (start+1) + "-" + end1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
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
        }
        countDownLatch.await();
        executorService.shutdown();
        for (Future<List<String>> future : futureList) {
            try {
                resultList.addAll(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void executorServiceTest(int size) throws InterruptedException {
        int threadNum = 4;
        List<String> data = getList(size);
        //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向上取整
        int perSize = data.size() < threadNum ? data.size() : (int) Math.ceil(Double.parseDouble(data.size() + "") / Double.parseDouble(threadNum + ""));
        //要处理的数据小于线程数量时，默认使用一个线程
        threadNum = data.size() < threadNum ? 1 : (int) Math.ceil(Double.parseDouble(data.size() + "") / Double.parseDouble(perSize + ""));
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(threadNum));
        final List<String> resultList = Collections.synchronizedList(new ArrayList<>());
        final List<Future<List<String>>> futureList = Collections.synchronizedList(new ArrayList<>());
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        System.out.println("tn:" + threadNum);
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * perSize;
            if (end > data.size()) {//计算出来的下标值超出了列表大小
                end = data.size();
                //threadNum = i;//重置循环条件，使下一次循环不再执行
            }

            final int k = i;
            final int start = i * perSize;
            final int end1 = end;
            final List<String> subList = data.subList(start, end);
            Future<List<String>> future = executorService.submit(() -> {
                List<String> result = new ArrayList<>();
                try {
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    System.out.println("当前线程" + k + "处理的数据量为:" + subList.size() + ",startTime:" + format.format(System.currentTimeMillis()) + ",s-e:" + (start+1) + "-" + end1);
                    for (String s : subList) {
                        result.add(s + "k");
                    }
                    System.out.println("当前线程" + k + "处理的数据量为:" + subList.size() + ",endTime:" + format.format(System.currentTimeMillis()) + ",s-e:" + (start+1) + "-" + end1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
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
        }
        countDownLatch.await();
        executorService.shutdown();
        for (Future<List<String>> future : futureList) {
            try {
                resultList.addAll(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void tt() {
        try {
            for(int i = 1; i <= 100; i++) {
                System.out.println("---------start:" + i + "------------");
                executorServiceTest3(i);
                System.out.println("-----------end:" + i + "------------");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executorServiceTest1() throws InterruptedException {
        int threadNum = 4;
        List<String> data = getList(2000000);
        //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向上取整
        int perSize = data.size() < threadNum ? data.size() : (int) Math.ceil(Double.parseDouble(data.size() + "") / Double.parseDouble(threadNum + ""));
        //要处理的数据小于线程数量时，默认使用一个线程
        threadNum = data.size() < threadNum ? 1 : threadNum;
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(threadNum));
        List<String> resultList = Collections.synchronizedList(new ArrayList<>());
        List<Callable<List<String>>> callables = new ArrayList<>(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * perSize;
            if (end > data.size()) {//计算出来的下标值超出了列表大小
                end = data.size();
                threadNum = i;//重置循环条件，使下一次循环不再执行
            }

            final int k = i;
            final List<String> subList = data.subList(i * perSize, end);
            Callable<List<String>> callable = new Callable<List<String>>() {
                @Override
                public List<String> call() {
                    System.out.println("当前线程" + k + "处理的数据量为:" + subList.size() + ",startTime:" + System.currentTimeMillis());
                    try {
                        Thread.sleep(10000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<String> result = new ArrayList<>();
                    for (String s : subList) {
                        result.add(s + "k");
                    }
                    System.out.println("当前线程" + k + "处理的数据量为:" + subList.size() + ",endTime:" + System.currentTimeMillis());
                    return result;
                }
            };
            callables.add(callable);
        }
        List<Future<List<String>>> futureList = executorService.invokeAll(callables);
        executorService.shutdown();
        System.out.println("futureSize:" + futureList.size());
        for (Future<List<String>> future : futureList) {
            try {
                resultList.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("resultSize:" + resultList.size());
    }

    @Test
    public void executorServiceTest2() throws ExecutionException, InterruptedException {
        int threadNum = 2;//默认开启两个线程

        List<String> list = getList(20000);
        //要处理的数据小于线程数量时，默认使用一个线程，且页大小就是数据集合的大小；要处理的数据大于线程数量时，页大小等于数据数量除以线程数并向上取整
        int perSize = list.size() < threadNum ? list.size() : (int) Math.ceil(Double.parseDouble(list.size() + "") / Double.parseDouble(threadNum + ""));
        //要处理的数据小于线程数量时，默认使用一个线程
        threadNum = list.size() < threadNum ? 1 : threadNum;

        final AtomicInteger atomicInteger = new AtomicInteger(-1);//定义原子量，用于定义线程名称
        //Runtime.getRuntime().availableProcessors();获取可用的处理器数量，可作为依据控制线程池线程数量
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> new Thread(r, "ChouZhouBatchPayThread-" + atomicInteger.incrementAndGet()));
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        List<String> msgAssembleDtoList = new ArrayList<>();
        final List<Future<List<String>>> futureList = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * perSize;
            if (end > list.size()) {//计算出来的下标值超出了列表大小
                end = list.size();
                threadNum = i;//重置循环条件，使下一次循环不再执行
            }

            List<String> subList = list.subList(i * perSize, end);
            System.out.println("当前线程处理的数据量为:" + subList.size());
            DeductionThread thread = new DeductionThread(subList, "buzDate", countDownLatch);
            Future<List<String>> future = executorService.submit(thread);
            futureList.add(future);
            System.out.println("当前线程:" + i);
        }
        countDownLatch.await();
        executorService.shutdown();

        if (!futureList.isEmpty()) {
            for (Future<List<String>> future : futureList) {
                if (future.get() != null && !future.get().isEmpty()) {
                    msgAssembleDtoList.addAll(future.get());
                }
            }
        }
    }

    /**
     * 同步线程
     */
    private class DeductionThread implements Callable<List<String>> {
        private final List<String> list;
        private final CountDownLatch countDownLatch;
        private final String buzDate;

        /**
         * 构造函数
         *
         * @param list           同步数据集
         * @param buzDate        业务日期
         * @param countDownLatch CountDownLatch对象
         */
        public DeductionThread(List<String> list, String buzDate, CountDownLatch countDownLatch) {
            this.list = list;
            this.buzDate = buzDate;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public List<String> call() throws InterruptedException {
            Instant now = Instant.now();
            System.out.println(Thread.currentThread().getName() + "开始时间:" + now.toString());
            Thread.sleep(10000L);
            System.out.println(Thread.currentThread().getName() + "结束时间:" + now.toString());
            System.out.println(Thread.currentThread().getName() + "执行时间:" + ChronoUnit.MILLIS.between(now, Instant.now()));
            countDownLatch.countDown();
            return list;
        }
    }

    @Test
    public void executorTest() {
        ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i++) {
            final int j = i;
            try {
                executorService.submit(() -> {
                    System.out.println(j + ":" + System.currentTimeMillis());
                });
                Thread.sleep((new Random().nextInt(1000) ));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Runnable> task = executorService.shutdownNow();
            System.out.println(j + ":" + System.currentTimeMillis());
        }
    }

    @Test
    public void listSplitTest() {
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
     * @param data 数据集合
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
