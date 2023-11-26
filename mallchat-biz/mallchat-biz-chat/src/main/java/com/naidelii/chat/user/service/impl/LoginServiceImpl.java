package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ILoginService;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
import com.naidelii.data.constant.RedisKey;
import com.naidelii.data.util.RedisUtils;
import com.naidelii.security.util.JwtUtils;
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
        // 生成token
        String token = JwtUtils.createToken(userId);
        // 将token存放到Redis中
        String key = RedisKey.getKey(RedisKey.PREFIX_USER_TOKEN, userId);
        RedisUtils.set(key, token, JwtUtils.getExpireTime());
        // 更新用户信息
        userService.updateById(onlineUser);
        // 返回token
        return token;
    }

}
