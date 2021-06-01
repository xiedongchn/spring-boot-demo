package com.xd.springbootdemo.redis.controller;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xd
 * Created on 2021/6/1
 */
@RestController
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;



}
