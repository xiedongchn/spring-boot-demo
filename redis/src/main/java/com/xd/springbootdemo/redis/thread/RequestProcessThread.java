package com.xd.springbootdemo.redis.thread;

import com.xd.springbootdemo.redis.request.AbstractRequest;
import com.xd.springbootdemo.redis.requestHandler.IRequestHandler;
import com.xd.springbootdemo.redis.request.impl.ProductQuotaDbUpdateRequest;
import com.xd.springbootdemo.redis.requestHandler.impl.ProductQuotaDbUpdateHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 请求处理线程
 *
 * @author xd
 * Created on 2021/6/1
 */
public class RequestProcessThread implements Callable<Integer> {

    private final ArrayBlockingQueue<AbstractRequest> queue;

    public RequestProcessThread(ArrayBlockingQueue<AbstractRequest> queue) {
        this.queue = queue;
    }

    @Override
    public Integer call() throws Exception {
        while (true) {
            AbstractRequest request = queue.take();
            boolean forceRefresh = request.isForceRefresh();

            IRequestHandler requestHandler;
            if (request instanceof ProductQuotaDbUpdateRequest) {
                requestHandler = new ProductQuotaDbUpdateHandler();
            } else {
                continue;
            }
            requestHandler.process(request);
        }
    }
}
