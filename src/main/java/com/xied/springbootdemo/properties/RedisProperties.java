package com.xied.springbootdemo.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 用于获取properties文件中redis的配置
 * @author xd
 * Created on 2018/7/23 14:50
 */
@Component
@PropertySource(value = "classpath:redis.properties")
public class RedisProperties {

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.pool.max-active}")
    private Integer maxActive;

    @Value("${spring.redis.pool.max-wait}")
    private Integer maxWait;

    @Value("${spring.redis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${sspring.redis.url}")
    private String url;

    /* 经测试，即使没有getter和setter方法，也能对属性进行注入 */

    public Integer getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public String getUrl() {
        return url;
    }
}
