package com.xd.springbootdemo.thread.endpoint;

import com.xd.springbootdemo.thread.TaskThreadPoolExecutor;
import com.xd.springbootdemo.thread.ThreadPoolManager;
import com.xd.springbootdemo.thread.config.DynamicThreadPoolProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程池信息端点
 */
public class ThreadPoolEndpoint {

    private ThreadPoolManager threadPoolManager;

    private DynamicThreadPoolProperties dynamicThreadPoolProperties;

    public Map<String, Object> threadPools() {
        Map<String, Object> data = new HashMap<>();

        List<Map> threadPools = new ArrayList<>();
        dynamicThreadPoolProperties.getExecutors().forEach(prop -> {
            TaskThreadPoolExecutor executor = threadPoolManager.getThreadPoolExecutor(prop.getThreadPoolName());
            AtomicLong rejectCount = threadPoolManager.getRejectCount(prop.getThreadPoolName());

            Map<String, Object> pool = new HashMap<>();
            pool.put("activeCount", executor.getActiveCount());
            pool.put("completedTaskCount", executor.getCompletedTaskCount());
            pool.put("largestPoolSize", executor.getLargestPoolSize());
            pool.put("taskCount", executor.getTaskCount());
            pool.put("rejectCount", rejectCount == null ? 0 : rejectCount.get());
            pool.put("waitTaskCount", executor.getQueue().size());
            threadPools.add(pool);
        });

        data.put("threadPools", threadPools);
        return data;
    }

}
