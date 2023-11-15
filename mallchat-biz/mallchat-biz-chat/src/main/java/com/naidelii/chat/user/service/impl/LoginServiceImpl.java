package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ILoginService;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
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
    public String loginByCode(String userId) {
        // 更新用户信息
        SysUser updateUser = UserAdapter.buildUpdateUser(userId);
        userService.updateById(updateUser);
        // 执行登录逻辑
        return null;
    }
}
