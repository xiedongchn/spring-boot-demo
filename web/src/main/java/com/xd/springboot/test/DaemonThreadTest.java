package com.xd.springboot.test;

public class DaemonThreadTest {
    public static void main(String[] args) {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    System.out.println(getName() + "...aaaaaaaaaaaaaaa" + i);
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println(getName() + "..bb" + i);
                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(getName() + "...cccccccccccccccccccccccccccc" + i);
                }
            }
        };

        t2.setDaemon(true);//setDaemon必须在setStart方法之前调用，因为运行中的线程不能设为守护线程
        t1.start();
        t2.start();
        t3.start();

        //t2设置为守护线程，当用户线程t1、t3执行完之后，t2还继续输出了一会才结束，但是并没有循环完50次，证明守护线程的生命周期依赖于用户线程
    }
}
