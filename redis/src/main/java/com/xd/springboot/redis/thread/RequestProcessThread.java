package com.xd.springboot.redis.thread;

import com.xd.springboot.redis.queue.RequestQueue;
import com.xd.springboot.redis.request.AbstractRequest;
import com.xd.springboot.redis.request.RequestManager;
import com.xd.springboot.redis.request.impl.ProductQuotaCacheUpdateRequest;
import com.xd.springboot.redis.request.impl.ProductQuotaDbUpdateRequest;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * 请求处理线程
 *
 * @author xd
 * Created on 2021/6/1
 */
@Log4j2
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

            if (!forceRefresh) {
                RequestQueue requestQueue = RequestQueue.getInstance();
                Map<Long, Boolean> flagMap = requestQueue.getFlagMap();
                if (request instanceof ProductQuotaDbUpdateRequest) {
                    flagMap.put(((ProductQuotaDbUpdateRequest) request).getProductId(), true);
                } else if (request instanceof ProductQuotaCacheUpdateRequest) {
                    Boolean flag = flagMap.get(((ProductQuotaCacheUpdateRequest) request).getProductId());
                    if (flag == null || flag) {
                        flagMap.put(((ProductQuotaCacheUpdateRequest) request).getProductId(), false);
                    }

                    if (flag != null && !flag) {
                        return 1;
                    }
                }
            }

            RequestManager.handle(request);
        }
    }
}
