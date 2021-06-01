package com.xd.springbootdemo.redis.queue;

import com.xd.springbootdemo.redis.requestHandler.IRequestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求内存队列缓存,配合线程池使用
 *
 * @author xd
 * Created on 2021/6/1
 */
public class RequestQueue {

    /**
     * 队列集合
     */
    private final List<ArrayBlockingQueue<IRequestHandler>> queues = new ArrayList<>();

    /**
     * 标志位map
     */
    public Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();

    private static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 构造私有
     */
    private RequestQueue() {}

    /**
     * 静态内部类单例,线程安全,Jvm保证内部类的初始化只会发生一次
     */
    private static class Singleton {
        private static final RequestQueue INSTANCE = new RequestQueue();

        public static RequestQueue getInstance() {
            return INSTANCE;
        }
    }

    /**
     * 添加一个内存队列
     *
     * @param queue 队列
     */
    public void addQueue(ArrayBlockingQueue<IRequestHandler> queue) {
        this.queues.add(queue);
    }

    /**
     * 获取队列数量
     *
     * @return int
     */
    public int queueSize() {
        return queues.size();
    }

    /**
     * 根据下标获取队列
     *
     * @param index 下标
     * @return ArrayBlockingQueue
     */
    public ArrayBlockingQueue<IRequestHandler> getQueue(int index) {
        return queues.get(index);
    }

}
