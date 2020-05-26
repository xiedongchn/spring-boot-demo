package com.xd.springbootdemo.utils;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

/**
 * @author xd
 * Created on 2018/7/22 09:16
 */
public class RedisUtil {

    public static void main(String[] args) {
        RedisProperties.Jedis jedis = new RedisProperties.Jedis();

    }
}
