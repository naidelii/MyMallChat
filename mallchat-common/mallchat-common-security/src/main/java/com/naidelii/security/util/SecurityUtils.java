package com.naidelii.security.util;

import com.naidelii.base.exception.NotLoginException;
import com.naidelii.security.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author naidelii
 */
@Slf4j
public final class SecurityUtils {

    /**
     * 私有化构造函数
     */
    private SecurityUtils() {

    }

    /**
     * 设置用户缓存到会话中
     */
    public static void setLoginUser(LoginUser loginUser) {
        // 存储到线程中
        SecurityContext.set(loginUser);
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public static LoginUser getLoginUser() {
        LoginUser loginUser = SecurityContext.get();
        if (loginUser == null) {
            throw new NotLoginException("该用户未登录！");
        }
        return loginUser;
    }

    /**
     * 删除用户信息
     */
    public static void removeLoginUser() {
        // 移除
        SecurityContext.remove();
    }

    /**
     * 获取用户名
     *
     * @return 已登录用户的昵称，如果未登录则返回 null。
     */
    public static String getUserName() {
        LoginUser loginUser = getLoginUser();
        return loginUser.getNickname();
    }

}
