package com.naidelii.chat.user.service.impl;

import com.naidelii.base.exception.MallChatException;
import com.naidelii.chat.user.dao.SysRoleDao;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ILoginService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
import com.naidelii.data.constant.RedisKey;
import com.naidelii.data.util.RedisUtils;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.JwtUtils;
import com.naidelii.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService {
    private final SysUserDao userDao;
    private final SysRoleDao roleDao;

    @Override
    public String scanQRCodeLogin(String userId) {
        return login(userId);
    }

    public String login(String userId) {
        // 获取用户信息
        LoginUser loginUser = getUserInfo(userId);
        // 生成token
        String token = JwtUtils.createToken(userId);
        // 将用户信息存储到上下文中
        SecurityUtils.setLoginUser(token, loginUser);
        // 将token存放到Redis中
        String key = RedisKey.getKey(RedisKey.PREFIX_USER_TOKEN, userId);
        RedisUtils.set(key, token, JwtUtils.getExpireTime());
        // 返回token
        return token;
    }

    private LoginUser getUserInfo(String userId) {
        SysUser user = userDao.getById(userId);
        if (user == null) {
            throw new MallChatException("该用户不存在");
        }
        // 查询用户的所有角色
        Set<String> roles = roleDao.getRolesByUserId(userId);
        return UserAdapter.buildLoginUser(user, roles);
    }

}
