package com.xd.springboot.common.thread;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 自定义ThreadPoolExecutor
 */
public class TaskThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * 线程池名称
     */
    private String threadPoolName = "defaultName";

    private final String defaultTaskName = "defaultTask";

    /**
     * The default rejected execution handler
     */
    private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();

    private final Map<String, String> runnableNameMap = new ConcurrentHashMap<>();

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, defaultHandler);
    }

    public TaskThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler, String threadPoolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.threadPoolName = threadPoolName;
    }

    @Override
    public void execute(Runnable command) {
        runnableNameMap.putIfAbsent(command.getClass().getSimpleName(), defaultTaskName);
        super.execute(command);
    }

    public void execute(Runnable command, String taskName) {
        runnableNameMap.putIfAbsent(command.getClass().getSimpleName(), taskName);
        super.execute(command);
    }

    public Future<?> submit(Runnable task, String taskName) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), taskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task, String taskName) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), taskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result, String taskName) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), taskName);
        return super.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), defaultTaskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), defaultTaskName);
        return super.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        runnableNameMap.putIfAbsent(task.getClass().getSimpleName(), defaultTaskName);
        return super.submit(task, result);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public Map<String, String> getRunnableNameMap() {
        return runnableNameMap;
    }
}
