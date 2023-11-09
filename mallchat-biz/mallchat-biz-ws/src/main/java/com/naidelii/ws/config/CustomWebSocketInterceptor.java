package com.naidelii.ws.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * 自定义拦截器拦截WebSocket请求
 * @author naidelii
 */
public class CustomWebSocketInterceptor implements HandshakeInterceptor {

    /**
     * 前置处理
     *
     * @param request    请求
     * @param response   响应
     * @param wsHandler  处理器
     * @param attributes 参数
     * @return 是否放行
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 可以做一些登录校验之类的
        return true;
    }

    /**
     * 后置处理
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}