package com.naidelii.websocket.config;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author naidelii
 */
@Configuration
@EnableConfigurationProperties(NettyProperties.class)
@RequiredArgsConstructor
public class NettyConfiguration {

    private final NettyProperties properties;

    /**
     * boss 线程池
     * 负责客户端连接
     *
     * @return NioEventLoopGroup
     */
    @Bean(name = "bossGroup")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(properties.getBossExecutor());
    }

    /**
     * worker线程池
     * 负责业务处理
     *
     * @return NioEventLoopGroup
     */
    @Bean(name = "workerGroup")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(properties.getWorkerExecutor());
    }

}
