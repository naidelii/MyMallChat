package com.naidelii.security.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.JwtUtils;
import com.naidelii.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author naidelii
 * 获取用户信息
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求中的token
        String token = request.getHeader(CommonConstants.AUTHORIZATION);
        if (StringUtils.isNotEmpty(token)) {
            // 解析token
            boolean verify = JwtUtils.verify(token);
            if (verify) {
                // 用户id
                String userId = JwtUtils.getUserInfo(token);
                LoginUser loginUser = new LoginUser();
                loginUser.setId(userId);
                // 用户客户端ip
                String clientIp = ServletUtil.getClientIP(request);
                loginUser.setClientIp(clientIp);
                // 放进上下文中
                SecurityUtils.setLoginUser(loginUser);
            }
        }
        // 通过验证
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityUtils.removeLoginUser();
    }
}
