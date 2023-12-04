package com.naidelii.data.aspect;

import com.naidelii.base.util.SpElUtils;
import com.naidelii.data.annotation.RedissonLock;
import com.naidelii.data.service.LockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author naidelii
 * Order(0)：确保比事务注解先执行，分布式锁在事务外
 */
@Slf4j
@Aspect
@Component
@Order(0)
public class RedissonLockAspect {

    @Autowired
    private LockService lockService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.naidelii.data.annotation.RedissonLock)")
    public void point() {

    }

    /**
     * 环绕
     *
     * @param joinPoint joinPoint
     * @return Object
     */
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) {
        // 获取对应的方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取指定注解
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        // 获取key前缀
        String prefixKey = redissonLock.prefixKey();
        prefixKey = StringUtils.isNotBlank(prefixKey) ? prefixKey : SpElUtils.getMethodName(method);
        // 获取key
        String key = redissonLock.key();
        key = StringUtils.isNotBlank(key) ? SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key()) : key;
        // 执行加锁逻辑
        return lockService.executeWithLockThrows((prefixKey + ":" + key), redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }

}
