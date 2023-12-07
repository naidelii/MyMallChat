package com.naidelii.chat.user.interceptor;

import cn.hutool.core.collection.CollUtil;
import com.naidelii.base.constant.enums.ResultCodeEnum;
import com.naidelii.chat.user.domain.enums.BlackTypeEnum;
import com.naidelii.chat.user.service.cache.BlackCache;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * @author naidelii
 * 黑名单拦截器
 */
@Component
public class BlackInterceptor implements HandlerInterceptor {

    @Autowired
    private BlackCache blackCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        String userId = loginUser.getId();
        String clientIp = loginUser.getClientIp();
        // 2.获取黑名单列表
        Map<Integer, Set<String>> blackMap = blackCache.getBlackMap();
        boolean userIdFlag = inBlackList(blackMap.get(BlackTypeEnum.USER_ID.getType()), userId);
        if (userIdFlag) {
            ResultCodeEnum.UNAUTHORIZED.sendHttpError(response);
            return false;
        }
        boolean ipFlag = inBlackList(blackMap.get(BlackTypeEnum.IP.getType()), clientIp);
        if (ipFlag) {
            ResultCodeEnum.UNAUTHORIZED.sendHttpError(response);
            return false;
        }
        return true;
    }

    private boolean inBlackList(Set<String> blacks, String target) {
        if (StringUtils.isEmpty(target) || CollUtil.isEmpty(blacks)) {
            return false;
        }
        return blacks.contains(target);
    }
}
