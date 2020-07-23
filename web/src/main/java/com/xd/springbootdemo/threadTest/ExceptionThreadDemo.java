package com.xd.springbootdemo.threadTest;

import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author xd
 * Created on 2020/4/20 22:02
 */
public class ExceptionThreadDemo {
    private static final int i = 1;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(Thread.currentThread().getState());
                    TimeUnit.SECONDS.sleep(1);//中断一个处于阻塞状态的线程，join/wait/queue.take
                    System.out.println(Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("i:" + i);
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//把isInterrupted设置为true

        System.out.println(thread.isInterrupted());//true
    }

}
