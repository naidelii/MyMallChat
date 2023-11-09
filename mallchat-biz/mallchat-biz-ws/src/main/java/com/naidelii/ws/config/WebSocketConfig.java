package com.naidelii.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * WebSocket 配置
 *
 * @author naidelii
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * 注册 WebSocket 处理器
     *
     * @param registry websocket处理器
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                // 设置连接路径和WebSocket处理器
                .addHandler(new CustomWebSocketHandler(), "/ws")
                // 允许跨域
                .setAllowedOrigins("*")
                // 设置WebSocket拦截器（可以做一些登录校验）
                .addInterceptors(new CustomWebSocketInterceptor());
    }

}
