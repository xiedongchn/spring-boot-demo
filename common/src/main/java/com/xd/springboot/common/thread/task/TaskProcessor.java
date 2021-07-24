package com.xd.springboot.common.thread.task;

import com.xd.springboot.common.thread.ThreadPoolManager;
import com.xd.springboot.common.thread.TaskThreadPoolExecutor;
import com.xd.springboot.common.thread.config.ThreadPoolProperties;
import com.xd.springboot.common.thread.enums.QueueTypeEnum;
import com.xd.springboot.common.thread.enums.RejectedExecutionHandlerEnum;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;

/**
 * 任务处理接口，有并发处理要求的业务，对应的类直接实现该接口，accept方法实现业务逻辑，即可达到并发处理的效果
 * 建议：重写initThreadPool方法，按需配置线程池，否则将采用默认的线程池配置初始化线程池，可能导致任务被拒绝
 *
 * @author xd
 * Created on 2021/3/16
 */
public interface TaskProcessor {

    /**
     * 默认的线程池初始化方法，提供默认的配置，最好按照需求重写
     *
     * @param code 业务代码
     */
    default TaskThreadPoolExecutor initThreadPool(String code) {
        ThreadPoolProperties properties = new ThreadPoolProperties();
        properties.setThreadPoolName(code);
        // 并发线程数默认设置为核心数
        properties.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        properties.setMaximumPoolSize(properties.getCorePoolSize());
        properties.setQueueCapacity(100);
        properties.setQueueType(QueueTypeEnum.LINKED_BLOCKING_QUEUE.getType());
        properties.setRejectedExecutionType(RejectedExecutionHandlerEnum.ABORT_POLICY.getType());
        return ThreadPoolManager.createThreadPoolExecutor(properties);
    }

    /**
     * 接受请求的方法，需要重写，对应的业务处理逻辑
     *
     * @param request 任务请求参数
     */
    TaskResponse accept(TaskRequest request);

    /**
     * 实际将任务放到线程池处理的方法
     *
     * @param request 任务请求参数
     * @return TaskResponse
     */
    default Future<TaskResponse> process(@NotNull TaskRequest request) {
        before(request);
        TaskThreadPoolExecutor executor = ThreadPoolManager.getThreadPoolExecutor(request.getCode());
        if (executor == null) {
            executor = initThreadPool(request.getCode());
        }
        assert executor != null;
        Future<TaskResponse> future = executor.submit(() -> accept(request));
        after(request, future);
        return future;
    }

    default void before(TaskRequest request) {
        if (StringUtils.isEmpty(request.getCode())) {
            throw new NullPointerException("Request code can't be null!");
        }
    }

    default void after(TaskRequest request, Future<TaskResponse> future) {
    }

}
