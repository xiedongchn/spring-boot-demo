package com.xd.springbootdemo.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xd
 * Created on 2021/6/1
 */
@Configuration
public class ThreadPoolConfig {

    private final int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 核心线程数量
     */
    private final int corePoolSize = 2;

    /**
     * 队列大小
     */
    private final int queueCapacity = 1000;

    /**
     * 线程一分钟后被杀死
     */
    private final int keepAliveSeconds = 60;

    /**
     * redis的缓存队列所使用的线程池
     *
     * @return
     */
    @Primary
    @Bean(name = "redisCacheQueueExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
