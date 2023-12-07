package com.naidelii.chat.user.interceptor;

import com.naidelii.base.constant.enums.ResultCodeEnum;
import com.naidelii.chat.user.dao.SysRoleDao;
import com.naidelii.chat.user.service.ISysRoleService;
import com.naidelii.security.annotation.RequiresRole;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author naidelii
 * 角色校验
 */
@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private SysRoleDao roleDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiresRole annotation = handlerMethod.getMethodAnnotation(RequiresRole.class);
            if (annotation != null) {
                // 获取当前用户的认证信息
                LoginUser loginUser = SecurityUtils.getLoginUser();
                // 获取当前用户的角色信息
                String userId = loginUser.getId();
                // 查询当前用户的角色列表
                Set<String> userRoles = roleDao.getRolesByUserId(userId);
                // 所需要的角色
                String[] requiredRoles = annotation.value();
                List<String> requiredRoleList = Arrays.asList(requiredRoles);
                // 检查用户是否具有所需的角色
                if (!userRoles.containsAll(requiredRoleList)) {
                    // 用户没有所需的角色，设置响应状态码为 403 Forbidden，并返回错误信息
                    ResultCodeEnum.FORBIDDEN.sendHttpError(response);
                    return false;
                }
                // 用户具有所需的角色，允许请求继续处理
                return true;
            }
        }
        // 如果没有RequiresRole注解，则放行
        return true;
    }
}
