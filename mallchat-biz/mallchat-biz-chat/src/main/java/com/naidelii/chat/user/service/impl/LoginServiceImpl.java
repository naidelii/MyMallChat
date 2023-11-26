package com.naidelii.chat.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ILoginService;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService {
    private final ISysUserService userService;

    @Override
    public String scanQRCodeLogin(String userId) {
        // 用户信息
        SysUser onlineUser = UserAdapter.buildOnlineUser(userId);
        // 执行登录逻辑
        LoginUser loginUser = UserAdapter.buildLoginUser(onlineUser);
        SecurityUtils.setLoginUser(userId, loginUser);
        // 更新用户信息
        userService.updateById(onlineUser);
        // 返回token
        return StpUtil.getTokenValue();
    }
}
