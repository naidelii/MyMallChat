package com.naidelii.data.util;

import cn.hutool.core.util.BooleanUtil;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.data.constant.RedisKey;
import com.naidelii.security.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * @author naidelii
 */
@Slf4j
public class TokenUtils {

    /**
     * 校验token是否有效
     *
     * @param token token
     * @return 是否有效
     */
    public static String getValidUserId(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        // 拿到token后，进行解密获取用户id
        String userId = JwtUtils.getUserInfo(token);
        // 使用jwt进行校验
        boolean verify = JwtUtils.verify(token);
        return BooleanUtil.isTrue(verify) ? userId : null;
    }

    public static boolean verify(String token) {
        String userId = JwtUtils.getUserInfo(token);
        if (userId == null) {
            return false;
        }
        // redis中token的key
        String key = RedisKey.getKey(RedisKey.PREFIX_USER_TOKEN, userId);
        // 有可能token失效了，需要校验是不是和最新token一致
        return Optional.ofNullable(RedisUtils.get(key))
                .map(cacheToken -> StringUtils.equals(token, String.valueOf(cacheToken)))
                .orElse(false);
    }

    /**
     * 刷新token（保证用户在线操作不掉线）
     */
    public static boolean jwtTokenRefresh(String token) {
        // 根据token获取用户id
        String userId = JwtUtils.getUserInfo(token);
        if (userId == null) {
            return false;
        }
        // redis中token的key
        String key = RedisKey.getKey(RedisKey.PREFIX_USER_TOKEN, userId);
        // 从redis中获取token信息
        String cacheToken = String.valueOf(RedisUtils.get(key));
        // 如果token已经失效则返回false
        if (StringUtils.isEmpty(cacheToken) || CommonConstants.NULL_STR.equals(cacheToken)) {
            return false;
        }
        // 如果redis中还有效，则判断jwt中失效没
        boolean verify = JwtUtils.verify(token);
        if (!verify) {
            // 如果jwt失效，则续期，重新生成token
            String newToken = JwtUtils.createToken(userId);
            RedisUtils.set(key, newToken, JwtUtils.getExpireTime());
        }
        return true;
    }

}
