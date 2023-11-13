package com.naidelii.chat.ws;

import com.naidelii.websocket.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NettyWebSocketServerBoot {

    private final ServerBootstrap serverBootstrap;
    private final NettyProperties nettyProperties;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    /**
     * 开机启动 ws server
     *
     * @throws InterruptedException 异常
     */
    @PostConstruct
    public void start() throws InterruptedException {
        // 绑定端口启动
        serverBootstrap.bind(nettyProperties.getPort()).sync();
        log.info("启动Netty Websocket服务器: {}", nettyProperties.getPort());
    }

    /**
     * 关闭 ws server
     *
     * @throws InterruptedException 异常
     */
    @PreDestroy
    public void close() throws InterruptedException {
        log.info("优雅得关闭Netty服务器");
        Future<?> future = bossGroup.shutdownGracefully();
        Future<?> future1 = workerGroup.shutdownGracefully();
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
    }

}
