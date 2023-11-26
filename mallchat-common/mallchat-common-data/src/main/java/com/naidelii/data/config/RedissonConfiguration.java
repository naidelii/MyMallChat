package com.naidelii.data.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author naidelii
 */

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfiguration {

    private final RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        // 配置类
        Config config = new Config();
        // 单机模式，也可以通过config.useClusterServers()添加集群地址
        String address = "redis://%s:%s";
        String info = String.format(address, redisProperties.getHost(), redisProperties.getPort());
        config.useSingleServer()
                .setAddress(info)
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());
        // 创建客户端
        return Redisson.create(config);
    }
}
