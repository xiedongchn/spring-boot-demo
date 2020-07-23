package com.xd.springbootdemo.designPatterns.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @see java.util.Observable 发布者
 * @see java.util.Observer 订阅者
 *
 * @author xd
 * Created on 2020/6/3 23:19
 */
public class ObserverDemo {

    public static void main(String[] args) {
        MyObservable myObservable = new MyObservable();

        myObservable.addObserver((o, arg) -> System.out.println("test"));

        myObservable.setChanged();//更新
        myObservable.notifyObservers();//通知订阅者
    }

    private static class MyObservable extends Observable {
        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }
    }

}
