package com.naidelii.security.util;

import com.naidelii.base.exception.NotLoginException;
import com.naidelii.security.entity.LoginUser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author naidelii
 * 上下文
 */
public class SecurityContext {

    /**
     * 上下文，存储登录用户的信息
     */
    private static final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 存储所有的用户信息
     */
    private static final Map<String, LoginUser> CONTEXT = new ConcurrentHashMap<>();

    protected static void set(String token, LoginUser loginUser) {
        CONTEXT.put(token, loginUser);
        THREAD_LOCAL.set(loginUser);
    }

    protected static LoginUser get() {
        return THREAD_LOCAL.get();
    }

    protected static void remove() {
        THREAD_LOCAL.remove();
    }

    public static void setLoginUser(String token) {
        LoginUser loginUser = CONTEXT.get(token);
        if (loginUser == null) {
            throw new NotLoginException("该用户未登录！");
        }
        THREAD_LOCAL.set(loginUser);
    }

}
