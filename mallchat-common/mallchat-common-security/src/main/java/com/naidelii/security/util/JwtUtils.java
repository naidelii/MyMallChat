package com.naidelii.security.util;

import cn.hutool.extra.spring.SpringUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.naidelii.security.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author naidelii
 */
@Slf4j
public class JwtUtils {

    /**
     * jwt配置信息
     */
    private static final JwtProperties PROPERTIES;


    static {
        PROPERTIES = SpringUtil.getBean(JwtProperties.class);
    }

    private static final String UID_CLAIM = "uid";

    /**
     * 获取过期时间
     *
     * @return 过期时间（单位s）
     */
    public static long getExpireTime() {
        // Redis中的过期时间设置为jwt的两倍
        return PROPERTIES.getExpireTime() * 2;
    }


    /**
     * 生成签名
     *
     * @param uid 用户id
     * @return 加密的token
     */
    public static String createToken(String uid) {
        // 单位（秒）
        long expireTime = PROPERTIES.getExpireTime();
        // 过期时间
        Date expire = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expireTime));
        Algorithm algorithm = Algorithm.HMAC256(PROPERTIES.getSecret());
        // 附带username信息
        return JWT.create()
                // 保存用户id
                .withClaim(UID_CLAIM, uid)
                // 过期时间
                .withExpiresAt(expire)
                .sign(algorithm);
    }

    /**
     * 校验token是否有效（过期会变无效）
     *
     * @param token token
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(PROPERTIES.getSecret());
            //创建token验证器
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();
            // 校验
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            // 抛出错误即为验证不通过
            return false;
        }
    }


    /**
     * 获得token中的信息（无需secret解密也能获得，过期了也能获得）
     *
     * @return token中包含的用户id
     */
    public static String getUserInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(UID_CLAIM).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
