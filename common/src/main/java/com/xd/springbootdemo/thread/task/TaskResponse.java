package com.xd.springbootdemo.thread.task;

/**
 * 任务请求响应基类
 *
 * @author xd
 * Created on 2021/3/17
 */
public class TaskResponse {

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return String.format("TaskResponse{code='%s', msg='%s'}", code, msg);
    }
}
