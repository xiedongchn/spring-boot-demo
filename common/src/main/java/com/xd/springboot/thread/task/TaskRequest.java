package com.xd.springboot.thread.task;

/**
 * 任务请求基类
 *
 * @author xd
 * Created on 2021/3/16
 */
public class TaskRequest {

    // 业务编码，用于分发到对应的线程池执行
    private String code;

    public TaskRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
