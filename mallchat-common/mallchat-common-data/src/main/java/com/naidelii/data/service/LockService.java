package com.naidelii.data.service;

import com.naidelii.base.exception.MallChatException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LockService {
    private final RedissonClient redissonClient;

    /**
     * 获取锁后执行指定方法
     *
     * @param key      key
     * @param waitTime waitTime
     * @param unit     unit
     * @param supplier supplier
     * @param <T>      t
     * @return 操作结果
     */
    @SneakyThrows
    public <T> T executeWithLockThrows(String key, int waitTime, TimeUnit unit, SupplierThrow<T> supplier) {
        // 获取锁对象
        RLock lock = redissonClient.getLock(key);
        // 获取锁
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new MallChatException("请求太频繁了，请稍后再试");
        }
        try {
            // 执行锁内的代码逻辑
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    public void executeWithLockThrows(String key, Runnable runnable) {
        executeWithLockThrows(key, -1, TimeUnit.MILLISECONDS, () -> {
            runnable.run();
            return null;
        });
    }

    /**
     * 函数式接口，用于提供结果并可能抛出异常。
     *
     * @param <T> 结果类型
     */
    @FunctionalInterface
    public interface SupplierThrow<T> {
        /**
         * 获取结果。
         *
         * @return 结果值
         * @throws Throwable 异常类型
         */
        T get() throws Throwable;
    }

}
