package com.naidelii.web;

import com.naidelii.data.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void testRedisUtils() {
        // 存储
        RedisUtils.set("name", "asdasd");
        // 读取
        Object name = RedisUtils.get("name");
        System.out.println(name);
    }

    @Test
    public void testRedisson() {
        // 锁的名称
        String lockKey = "anyLock";
        // 获取锁对象（可重入）
        RLock lock = redisson.getLock(lockKey);
        try {
            /**
             * 获取锁，tryLock(waitTime,leseTime,unit)
             *     waitTime：重试获取锁的最大等待时间，默认为-1，不重试
             *     leseTime：锁超时释放时间，默认30
             *     unit：超时释放时间单位，默认秒
             */
            boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
            // 判断锁获取释放成功
            if (isLock) {
                try {
                    // 执行业务
                    System.out.println("执行业务");
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
