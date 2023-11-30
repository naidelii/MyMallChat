package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.user.service.ILoginService;
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

    @Override
    public String scanQRCodeLogin(String userId) {
        // 生成token
        String token = JwtUtils.createToken(userId);
        // 将token存放到Redis中
        String key = RedisKey.getKey(RedisKey.PREFIX_USER_TOKEN, userId);
        RedisUtils.set(key, token, JwtUtils.getExpireTime());
        // 返回token
        return token;
    }

}
