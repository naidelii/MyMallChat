package com.naidelii.chat.ws.service;

import com.naidelii.chat.ws.domain.enums.WebSocketRequestTypeEnum;
import com.naidelii.chat.ws.domain.vo.request.WebSocketRequestMessage;

/**
 * @author naidelii
 * WebSocket 请求处理策略接口
 */
public interface WebSocketRequestMessageStrategyHandler {

    /**
     * 处理消息
     * @param data 消息实体
     */
    void handle(WebSocketRequestMessage data);

    /**
     * 操作类型
     * @return 操作类型
     */
    WebSocketRequestTypeEnum type();

}
