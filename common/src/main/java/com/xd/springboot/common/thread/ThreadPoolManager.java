package com.xd.springboot.common.thread;

import com.xd.springboot.common.thread.config.DynamicThreadPoolProperties;
import com.xd.springboot.common.thread.config.ThreadPoolProperties;
import com.xd.springboot.common.thread.enums.QueueTypeEnum;
import com.xd.springboot.common.thread.enums.RejectedExecutionHandlerEnum;
import com.xd.springboot.common.thread.queue.ResizableCapacityLinkedBlockingQueue;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 动态线程池，参考文章：
 * 美团：https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
 * 尹吉欢：https://www.cnblogs.com/yinjihuan/p/13151887.html，项目地址：https://github.com/yinjihuan/kitty
 */
public class ThreadPoolManager {

    //使用@Autowired注入配置，实现动态调整线程池大小
    //@Autowired
    private DynamicThreadPoolProperties dynamicThreadPoolProperties;

    /**
     * 存储线程池对象，Key:名称 Value:对象
     */
    private static final Map<String, TaskThreadPoolExecutor> threadPoolExecutorMap = new HashMap<>();

    /**
     * 存储线程池拒绝次数，Key:名称 Value:次数
     */
    private static final Map<String, AtomicLong> threadPoolExecutorRejectCountMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        createThreadPoolExecutor(dynamicThreadPoolProperties);
    }

    /**
     * 创建线程池
     *
     * @param threadPoolProperties 线程池属性
     */
    public static void createThreadPoolExecutor(DynamicThreadPoolProperties threadPoolProperties) {
        threadPoolProperties.getExecutors().forEach(ThreadPoolManager::createThreadPoolExecutor);
    }

    /**
     * 依据线程池属性创建线程池
     *
     * @param threadPoolProperties 线程池属性
     */
    public static TaskThreadPoolExecutor createThreadPoolExecutor(ThreadPoolProperties threadPoolProperties) {
        if (!threadPoolExecutorMap.containsKey(threadPoolProperties.getThreadPoolName())) {
            synchronized (threadPoolExecutorMap) {
                if (!threadPoolExecutorMap.containsKey(threadPoolProperties.getThreadPoolName())) {
                    TaskThreadPoolExecutor threadPoolExecutor = new TaskThreadPoolExecutor(
                            threadPoolProperties.getCorePoolSize(), threadPoolProperties.getMaximumPoolSize(),
                            threadPoolProperties.getKeepAliveTime(), threadPoolProperties.getUnit(),
                            getBlockingQueue(threadPoolProperties.getQueueType(), threadPoolProperties.getQueueCapacity(), threadPoolProperties.isFair()),
                            new DefaultThreadFactory(threadPoolProperties.getThreadPoolName()),
                            getRejectedExecutionHandler(threadPoolProperties.getRejectedExecutionType(), threadPoolProperties.getThreadPoolName()), threadPoolProperties.getThreadPoolName());
                    threadPoolExecutorMap.put(threadPoolProperties.getThreadPoolName(), threadPoolExecutor);
                }
            }
        }
        return threadPoolExecutorMap.get(threadPoolProperties.getThreadPoolName());
    }

    public void registerStatusExtension(ThreadPoolProperties prop, TaskThreadPoolExecutor executor) {
        /*StatusExtensionRegister.getInstance().register(new StatusExtension() {
            @Override
            public String getId() {
                return "thread.pool.info." + prop.getThreadPoolName();
            }

            @Override
            public String getDescription() {
                return "线程池监控";
            }

            @Override
            public Map<String, String> getProperties() {
                AtomicLong rejectCount = getRejectCount(prop.getThreadPoolName());

                Map<String, String> pool = new HashMap<>();

                pool.put("activeCount", String.valueOf(executor.getActiveCount()));
                pool.put("completedTaskCount", String.valueOf(executor.getCompletedTaskCount()));
                pool.put("largestPoolSize", String.valueOf(executor.getLargestPoolSize()));
                pool.put("taskCount", String.valueOf(executor.getTaskCount()));
                pool.put("rejectCount", String.valueOf(rejectCount == null ? 0 : rejectCount.get()));
                pool.put("waitTaskCount", String.valueOf(executor.getQueue().size()));
                return pool;
            }
        });*/
    }

    /**
     * 获取拒绝策略
     *
     * @param rejectedExecutionType 拒绝策略
     * @param threadPoolName        线程池名称
     * @return RejectedExecutionHandler
     */
    private static RejectedExecutionHandler getRejectedExecutionHandler(String rejectedExecutionType, String threadPoolName) {
        if (RejectedExecutionHandlerEnum.CALLER_RUNS_POLICY.getType().equals(rejectedExecutionType)) {
            return new ThreadPoolExecutor.CallerRunsPolicy();
        }
        if (RejectedExecutionHandlerEnum.DISCARD_OLDEST_POLICY.getType().equals(rejectedExecutionType)) {
            return new ThreadPoolExecutor.DiscardOldestPolicy();
        }
        if (RejectedExecutionHandlerEnum.DISCARD_POLICY.getType().equals(rejectedExecutionType)) {
            return new ThreadPoolExecutor.DiscardPolicy();
        }
        ServiceLoader<RejectedExecutionHandler> serviceLoader = ServiceLoader.load(RejectedExecutionHandler.class);
        for (RejectedExecutionHandler rejectedExecutionHandler : serviceLoader) {
            String rejectedExecutionHandlerName = rejectedExecutionHandler.getClass().getSimpleName();
            if (rejectedExecutionType.equals(rejectedExecutionHandlerName)) {
                return rejectedExecutionHandler;
            }
        }
        return new DefaultAbortPolicy(threadPoolName);
    }

    /**
     * 获取阻塞队列
     *
     * @param queueType     队列类型
     * @param queueCapacity 队列容量
     * @param fair          是否公平
     * @return BlockingQueue
     */
    private static BlockingQueue<Runnable> getBlockingQueue(String queueType, int queueCapacity, boolean fair) {
        if (!QueueTypeEnum.exists(queueType)) {
            throw new RuntimeException("队列不存在 " + queueType);
        }
        if (QueueTypeEnum.ARRAY_BLOCKING_QUEUE.getType().equals(queueType)) {
            return new ArrayBlockingQueue<>(queueCapacity);
        }
        if (QueueTypeEnum.SYNCHRONOUS_QUEUE.getType().equals(queueType)) {
            return new SynchronousQueue<>(fair);
        }
        if (QueueTypeEnum.PRIORITY_BLOCKING_QUEUE.getType().equals(queueType)) {
            return new PriorityBlockingQueue<>(queueCapacity);
        }
        if (QueueTypeEnum.DELAY_QUEUE.getType().equals(queueType)) {
            return new DelayQueue();
        }
        if (QueueTypeEnum.LINKED_BLOCKING_DEQUE.getType().equals(queueType)) {
            return new LinkedBlockingDeque<>(queueCapacity);
        }
        if (QueueTypeEnum.LINKED_TRANSFER_DEQUE.getType().equals(queueType)) {
            return new LinkedTransferQueue<>();
        }
        return new ResizableCapacityLinkedBlockingQueue<>(queueCapacity);
    }

    public static TaskThreadPoolExecutor getThreadPoolExecutor(String threadPoolName) {
        return threadPoolExecutorMap.get(threadPoolName);
    }

    public AtomicLong getRejectCount(String threadPoolName) {
        return threadPoolExecutorRejectCountMap.get(threadPoolName);
    }

    public void clearRejectCount(String threadPoolName) {
        threadPoolExecutorRejectCountMap.remove(threadPoolName);
    }

    static class DefaultAbortPolicy implements RejectedExecutionHandler {
        private final String threadPoolName;

        public DefaultAbortPolicy(String threadPoolName) {
            this.threadPoolName = threadPoolName;
        }

        /**
         * Always throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws RejectedExecutionException always
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            AtomicLong atomicLong = threadPoolExecutorRejectCountMap.putIfAbsent(threadPoolName, new AtomicLong(1));
            if (atomicLong != null) {
                atomicLong.incrementAndGet();
            }
            throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + e.toString());
        }
    }

    /**
     * 获取线程池信息
     *
     * @return List
     */
    public static List<Map<String, Object>> getThreadPoolInfo() {
        List<Map<String, Object>> infoList = new ArrayList<>();
        for (Map.Entry<String, TaskThreadPoolExecutor> entry : threadPoolExecutorMap.entrySet()) {
            Map<String, Object> info = new HashMap<>();
            info.put("name", entry.getValue().getThreadPoolName());
            info.put("activeCount", entry.getValue().getActiveCount());
            info.put("completedTaskCount", entry.getValue().getCompletedTaskCount());
            info.put("largestPoolSize", entry.getValue().getLargestPoolSize());
            info.put("taskCount", entry.getValue().getTaskCount());
            info.put("waitTaskCount", entry.getValue().getQueue().size());
            AtomicLong rejectCount = threadPoolExecutorRejectCountMap.get(entry.getKey());
            info.put("rejectCount", rejectCount == null ? 0 : rejectCount.get());
            info.put("runnableCount", entry.getValue().getRunnableNameMap().size());
            infoList.add(info);
        }
        return infoList;
    }

}
