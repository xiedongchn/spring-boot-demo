package com.xd.springbootdemo.test;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author xd
 * Created on 2020/8/19
 */
public class TestThreadLocal {
    ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<>();

    @Test
    public void testThreadLocal() throws InterruptedException {
        int dataSize = 57;
        int threadNum = 5;
        ConcurrentMap<String, StringBuilder> map = new ConcurrentHashMap<>(threadNum + 1);
        final CountDownLatch latch = new CountDownLatch(dataSize);
        LinkedBlockingDeque<Runnable> deque = new LinkedBlockingDeque<>(50);
        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.SECONDS, deque, new ThreadPoolExecutor.CallerRunsPolicy());
        //System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < dataSize; i++) {
            executorService.execute(() -> {
                /*Instant now = Instant.now();
                System.out.println(Thread.currentThread().getName() + ",st:" + now.toString());
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ",in:" + ChronoUnit.MILLIS.between(now, Instant.now()));*/
                /*try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                if (threadLocal.get() == null) {
                    threadLocal.set(new StringBuilder());
                    map.put(Thread.currentThread().getName(), threadLocal.get());
                }
                System.out.println("执行线程:" + Thread.currentThread().getName() + "获取的sdf:" + threadLocal.get().hashCode());
                latch.countDown();
            });
        }
        Instant now = Instant.now();
        System.out.println("End of loop.");
        latch.await(60, TimeUnit.MINUTES);
        System.out.println("End of loop:" + ChronoUnit.MILLIS.between(now, Instant.now()));
        executorService.shutdown();

        for (Map.Entry<String, StringBuilder> entry : map.entrySet()) {
            System.out.println("销毁线程:" + entry.getKey() + ",sdf:" + entry.getValue().hashCode());
        }
    }

    /**
     * Here is some code to clean all thread local variables from the current thread when you do not have a reference
     * to the actual thread local variable. You can also generalize it to cleanup thread local variables for other threads:
     */
    public static void clearThreadLocal() {
        try {
            // Get a reference to the thread locals table of the current thread
            Thread thread = Thread.currentThread();
            Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            Object threadLocalTable = threadLocalsField.get(thread);

            // Get a reference to the array holding the thread local variables inside the
            // ThreadLocalMap of the current thread
            Class<?> threadLocalMapClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
            Field tableField = threadLocalMapClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object table = tableField.get(threadLocalTable);

            // The key to the ThreadLocalMap is a WeakReference object. The referent field of this object
            // is a reference to the actual ThreadLocal variable
            Field referentField = Reference.class.getDeclaredField("referent");
            referentField.setAccessible(true);

            for (int i = 0; i < Array.getLength(table); i++) {
                // Each entry in the table array of ThreadLocalMap is an Entry object
                // representing the thread local reference and its value
                Object entry = Array.get(table, i);
                if (entry != null) {
                    // Get a reference to the thread local object and remove it from the table
                    ThreadLocal<?> threadLocal = (ThreadLocal<?>)referentField.get(entry);
                    threadLocal.remove();
                }
            }
        } catch(Exception e) {
            // We will tolerate an exception here and just log it
            throw new IllegalStateException(e);
        }
    }
}
