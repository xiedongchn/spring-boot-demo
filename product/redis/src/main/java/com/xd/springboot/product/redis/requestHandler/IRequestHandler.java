package com.xd.springboot.product.redis.requestHandler;

/**
 * @author xd
 * Created on 2021/6/1
 */
public interface IRequestHandler<T> {

    /**
     * 请求处理
     *
     * @param t 请求
     */
    void process(T t);

    /**
     * 是否强制刷新
     *
     * @return boolean
     */
    boolean isForceRefresh();
}
