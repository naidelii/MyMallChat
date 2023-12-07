package com.naidelii.security.interceptor;

import com.naidelii.base.constant.CommonConstants;
import com.naidelii.security.util.JwtUtils;
import com.naidelii.security.util.SecurityContext;
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
                // 通过token获取用户信息设置进上下文
                SecurityContext.setLoginUser(token);
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
