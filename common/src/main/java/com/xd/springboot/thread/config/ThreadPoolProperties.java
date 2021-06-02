package com.xd.springboot.thread.config;

import com.xd.springboot.thread.enums.QueueTypeEnum;
import com.xd.springboot.thread.enums.RejectedExecutionHandlerEnum;

import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 */
public class ThreadPoolProperties {

    /**
     * 线程池名称
     */
    private String threadPoolName = "DefaultThreadPool";

    /**
     * 核心线程数
     */
    private int corePoolSize = 1;

    /**
     * 最大线程数, 默认值为CPU核心数量
     */
    private int maximumPoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 队列最大数量
     */
    private int queueCapacity = Integer.MAX_VALUE;

    /**
     * 队列类型
     *
     * @see QueueTypeEnum
     */
    private String queueType = QueueTypeEnum.LINKED_BLOCKING_QUEUE.getType();

    /**
     * SynchronousQueue 是否公平策略
     */
    private boolean fair;

    /**
     * 拒绝策略
     *
     * @see RejectedExecutionHandlerEnum
     */
    private String rejectedExecutionType = RejectedExecutionHandlerEnum.ABORT_POLICY.getType();

    /**
     * 空闲线程存活时间
     */
    private long keepAliveTime;

    /**
     * 空闲线程存活时间单位
     */
    private TimeUnit unit = TimeUnit.MILLISECONDS;

    /**
     * 队列容量阀值，超过此值告警
     */
    private int queueCapacityThreshold = queueCapacity;

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public boolean isFair() {
        return fair;
    }

    public void setFair(boolean fair) {
        this.fair = fair;
    }

    public String getRejectedExecutionType() {
        return rejectedExecutionType;
    }

    public void setRejectedExecutionType(String rejectedExecutionType) {
        this.rejectedExecutionType = rejectedExecutionType;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public int getQueueCapacityThreshold() {
        return queueCapacityThreshold;
    }

    public void setQueueCapacityThreshold(int queueCapacityThreshold) {
        this.queueCapacityThreshold = queueCapacityThreshold;
    }

    @Override
    public String toString() {
        return String.format("ThreadPoolProperties{threadPoolName='%s', corePoolSize=%d, maximumPoolSize=%d, queueCapacity=%d, queueType='%s', fair=%s, rejectedExecutionType='%s', keepAliveTime=%d, unit=%s, queueCapacityThreshold=%d}", threadPoolName, corePoolSize, maximumPoolSize, queueCapacity, queueType, fair, rejectedExecutionType, keepAliveTime, unit, queueCapacityThreshold);
    }
}
