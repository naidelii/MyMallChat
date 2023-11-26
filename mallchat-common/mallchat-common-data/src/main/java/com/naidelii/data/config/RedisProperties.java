package com.naidelii.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author naidelii
 */
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisProperties {

    /**
     * 数据库索引
     */
    private int database;

    /**
     * 服务器地址
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * 密码
     */
    private String password;
}
