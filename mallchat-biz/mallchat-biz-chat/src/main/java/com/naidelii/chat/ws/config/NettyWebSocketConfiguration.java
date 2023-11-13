package com.naidelii.chat.ws.config;

import com.naidelii.chat.ws.service.handler.WebSocketConnectionHandler;
import com.naidelii.websocket.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
public class NettyWebSocketConfiguration {

    private final NettyProperties properties;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;

    /**
     * 服务器启动器
     *
     * @return ServerBootstrap
     */
    @Bean
    public ServerBootstrap serverBootstrap() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 指定使用的线程组
                .group(bossGroup, workerGroup)
                // 指定使用的通道（ServerSocketChannel是以NIO的selector为基础进行实现的，
                // 用来接收新的连接，这里告诉Channel通过NioServerSocketChannel获取新的连接）
                .channel(NioServerSocketChannel.class)
                // 指定连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getTimeout())
                // 指定worker处理器
                .childHandler(new WebSocketConnectionHandler());
        return serverBootstrap;
    }

}
