package com.naidelii.security.util;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.naidelii.security.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;

/**
 * @author naidelii
 */
@Slf4j
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
    public static void setLoginUser(String userId, LoginUser loginUser) {
        StpUtil.login(userId);
        StpUtil.getTokenSession().set(USER_KEY, loginUser);
        log.info("用户 '{}:{}' 登录了。", userId, loginUser.getNickname());
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户
     */
    public static LoginUser getLoginUser() {
        try {
            // 获取会话信息
            SaSession session = StpUtil.getTokenSession();
            if (session != null) {
                return (LoginUser) session.get(USER_KEY);
            } else {
                log.warn("尝试获取登录用户时会话为空。");
                return null;
            }
        } catch (Exception e) {
            log.error("获取登录用户时出错：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 获取用户名
     *
     * @return 已登录用户的昵称，如果未登录则返回 null。
     */
    public static String getUserName() {
        LoginUser loginUser = getLoginUser();
        if (loginUser != null) {
            return loginUser.getNickname();
        }
        log.warn("在尝试获取用户名时未找到已登录用户。");
        return null;
    }

}
