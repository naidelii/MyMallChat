package com.naidelii.security.interceptor;

import com.naidelii.base.constant.CommonConstants;
import com.naidelii.base.constant.enums.ResultCodeEnum;
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
        if (StringUtils.isNotEmpty(token) && JwtUtils.verify(token)) {
            // 通过验证
            return true;
        }
        // 无效，返回401
        ResultCodeEnum.UNAUTHORIZED.sendHttpError(response);
        return false;
    }
}
