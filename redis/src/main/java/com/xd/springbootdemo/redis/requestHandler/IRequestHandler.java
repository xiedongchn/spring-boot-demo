package com.xd.springbootdemo.redis.requestHandler;

/**
 * @author xd
 * Created on 2021/6/1
 */
public interface IRequestHandler<T> {

    void process(T t);

    boolean isForceRefresh();
}
