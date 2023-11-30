package com.naidelii.security.util;

import com.naidelii.security.entity.LoginUser;

/**
 * @author naidelii
 * 上下文
 */
public class SecurityContext {

    /**
     * 上下文，存储登录用户的信息
     */
    private static final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    protected static void set(LoginUser loginUser) {
        THREAD_LOCAL.set(loginUser);
    }

    protected static LoginUser get() {
        return THREAD_LOCAL.get();
    }

    protected static void remove() {
        THREAD_LOCAL.remove();
    }

}
