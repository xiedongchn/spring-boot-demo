package com.xd.springboot.thread.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态线程池配置
 */
public class DynamicThreadPoolProperties {

    /**
     * 线程池配置
     */
    private List<ThreadPoolProperties> executors = new ArrayList<>();

    public List<ThreadPoolProperties> getExecutors() {
        return executors;
    }

    public void setExecutors(List<ThreadPoolProperties> executors) {
        this.executors = executors;
    }
}
