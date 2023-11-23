package com.naidelii.security.util;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.naidelii.security.entity.LoginUser;

/**
 * @author naidelii
 */
public final class SecurityUtils {
    private static final String USER_KEY = "boot";

    /**
     * 私有化构造函数
     */
    private SecurityUtils() {

    }

    /**
     * 设置用户缓存到会话中
     */
    public static void setLoginUser(String userName, LoginUser loginUser) {
        StpUtil.login(userName);
        StpUtil.getTokenSession().set(USER_KEY, loginUser);
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public static LoginUser getLoginUser() {
        // 获取会话信息
        SaSession tokenSession = StpUtil.getTokenSession();
        return (LoginUser) tokenSession.get(USER_KEY);
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public static String getUserName() {
        return getLoginUser().getNickname();
    }

}
