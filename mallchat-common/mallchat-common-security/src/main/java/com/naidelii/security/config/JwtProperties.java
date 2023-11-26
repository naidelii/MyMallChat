package com.naidelii.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author naidelii
 */
@ConfigurationProperties(prefix = "jwt")
@Configuration
@Data
public class JwtProperties {

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private long expireTime;
}
