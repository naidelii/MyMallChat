package com.naidelii.data.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Naidelii
 * 分布式锁
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {


    /**
     * key的前缀，对不同的业务作区分，默认为方法全限定名
     *
     * @return key的前缀
     */
    String prefixKey() default "";

    /**
     * redis加锁的key，支持springEl表达式
     *
     * @return redis 锁key
     */
    String key() default "";

    /**
     * 等待锁的时间单位，默认是秒
     *
     * @return 单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 等待锁的时间，默认-1快速失败（不等待直接失败）
     *
     * @return 单位秒
     */
    int waitTime() default -1;

}
