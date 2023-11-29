package com.naidelii.security.interceptor;

import com.naidelii.base.constant.CommonConstants;
import com.naidelii.security.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author naidelii
 */
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求中的token
        String token = request.getHeader(CommonConstants.AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        // 校验token是否有效
        boolean verify = JwtUtils.verify(token);
        if (!verify) {
            // 无效，返回401
            response.setContentType("application/json");
            response.setStatus(401);
            return false;
        }
        // 通过验证
        return true;
    }
}
