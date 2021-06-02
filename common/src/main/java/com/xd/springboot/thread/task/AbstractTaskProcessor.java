package com.xd.springboot.thread.task;

import com.xd.springboot.thread.TaskThreadPoolExecutor;
import com.xd.springboot.thread.ThreadPoolManager;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;

/**
 * 线程分发
 *
 * @author xd
 * Created on 2021/3/16
 */
public abstract class AbstractTaskProcessor {

    protected abstract TaskResponse accept(TaskRequest request);

    public final Future<TaskResponse> process(@NotNull TaskRequest request) {
        before(request);
        TaskThreadPoolExecutor executor = ThreadPoolManager.getThreadPoolExecutor(request.getCode());
        if (executor == null) {
            throw new NullPointerException("Executor is null, code:" + request.getCode());
        }
        Future<TaskResponse> future = executor.submit(() -> accept(request));
        after(request, future);
        return future;
    }

    public void before(TaskRequest request) {
        if (StringUtils.isEmpty(request.getCode())) {
            throw new NullPointerException("Request code can't be null!");
        }
    }

    public void after(TaskRequest request, Future<TaskResponse> future) {
    }

}
