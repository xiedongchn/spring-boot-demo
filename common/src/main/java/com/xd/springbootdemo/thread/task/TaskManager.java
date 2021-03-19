package com.xd.springbootdemo.thread.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * 任务分发
 *
 * @author xd
 * Created on 2021/3/17
 */
public class TaskManager {
    private volatile static TaskManager manager;

    private final Map<String, AbstractTaskProcessor> processorMap = new ConcurrentHashMap<>();

    /**
     * 任务分发逻辑，依据请求参数的Code
     *
     * @param request 请求对象
     * @return TaskResponse
     */
    public Future<TaskResponse> dispatch(TaskRequest request) {
        return TaskManager.getInstance().getProcessor(request.getCode()).process(request);
    }

    private TaskManager() {}

    public static TaskManager getInstance() {
        if (manager == null) {
            synchronized (TaskManager.class) {
                if (manager == null) {
                    manager = new TaskManager();
                }
            }
        }
        return manager;
    }

    public AbstractTaskProcessor getProcessor(String code) {
        return getInstance().processorMap.get(code);
    }

    public static void registerProcessor(String code, AbstractTaskProcessor processor) {
        getInstance().processorMap.put(code, processor);
    }
}
