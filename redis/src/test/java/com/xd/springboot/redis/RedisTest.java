package com.xd.springboot.redis;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class RedisTest {

    private RedissonClient redissonClient;

    @Before
    public void init() {
        Config config = new Config();
        //config.setTransportMode(TransportMode.EPOLL);
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer().setAddress("redis://10.211.55.5:6379").setPassword("123456");
        redissonClient = Redisson.create(config);
    }

    @Test
    public void testLuaScript2() {
        Long roomId = 123456L;

        // 准备数据结构：（1）存储roomId，key为room， field为roomId， value为123456
        // （2）存储123456房间中用户列表，key为roomId.123456， value为字符串数组对象
        String hsetRoomId = "redis.call('HSET', KEYS[1], ARGV[1], ARGV[2]);" +
                "return redis.call('HGET', KEYS[1], '\"roomId\"');";
        RScript rScript = redissonClient.getScript();
        Long l = rScript.eval(RScript.Mode.READ_WRITE, hsetRoomId, RScript.ReturnType.INTEGER,
                Lists.newArrayList("room"),
                Lists.newArrayList("roomId", roomId).toArray());
        String getRoomUserList =
                // roomIdJson = ["java.lang.Long",123456]
                "local roomIdJson = redis.call('HGET', KEYS[1], '\"roomId\"'); " +
                        "local roomId = " +
                        "       string.sub(roomIdJson, string.find(roomIdJson, ',') + 1, string.len(roomIdJson) - 1); " +
                        "return redis.call('GET', 'roomId' .. '.' .. roomId); ";
        ArrayList result = rScript.eval(RScript.Mode.READ_WRITE, getRoomUserList, RScript.ReturnType.VALUE,
                Lists.newArrayList("room"),
                new Object[0]);
        Assert.assertTrue(result.contains("Jack"));
    }

}
