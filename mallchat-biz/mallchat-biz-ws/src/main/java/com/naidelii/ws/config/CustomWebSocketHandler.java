package com.naidelii.ws.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket
 *
 * @author equetion
 * @date 2022-05-30
 */
@Component
@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {

    /**
     * 固定前缀
     */
    private static final String USER_ID = "user_id_";

    /**
     * 存放 Session集合，推送消息
     */
    private static final ConcurrentHashMap<String, WebSocketSession> WEB_SOCKET_SESSION_MAPS = new ConcurrentHashMap<>();

    /**
     * 连接开启
     *
     * @param session 会话
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = session.getAttributes().get("userId").toString();
        WEB_SOCKET_SESSION_MAPS.put(USER_ID + userId, session);
        String message = session.getId() + "【" + userId + "】已连接";
        log.info(message);
        sendMessage(session, message);
    }

    /**
     * 连接关闭
     *
     * @param session 会话
     * @param status 状态
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = session.getAttributes().get("userId").toString();
        WEB_SOCKET_SESSION_MAPS.remove(USER_ID + userId);
        log.info(session.getId() + "【" + userId + "】连接关闭");
    }

    /**
     * 收到消息
     *
     * @param session session
     * @param message 消息
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("服务端收到来自【" + session.getId() + "】消息：" + message);
    }

    /**
     * 向指定客户端推送消息
     *
     * @param session session
     * @param message 消息
     */
    public void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向指定用户群体推送消息
     *
     * @param userIds 用户集合
     * @param message 消息
     */
    public void sendMoreMessage(List<String> userIds, String message) {
        for (String userId : userIds) {
            sendMessage(userId, message);
        }
    }

    /**
     * 向指定用户推送消息
     *
     * @param userId  用户
     * @param message 消息
     */
    public void sendMessage(String userId, String message) {
        WebSocketSession session = WEB_SOCKET_SESSION_MAPS.get(USER_ID + userId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:" + message);
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }


    /**
     * 向所有用户推送消息
     *
     * @param message 消息
     */
    public void sendMessageAllUser(String message) {
        for (WebSocketSession session : WEB_SOCKET_SESSION_MAPS.values()) {
            if (session != null) {
                sendMessage(session, message);
            }
        }
    }
}
