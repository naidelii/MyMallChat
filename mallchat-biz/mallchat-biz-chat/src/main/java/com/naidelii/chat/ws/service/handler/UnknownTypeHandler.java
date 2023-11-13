package com.naidelii.chat.ws.service.handler;

import com.naidelii.chat.ws.domain.enums.WebSocketRequestTypeEnum;
import com.naidelii.chat.ws.domain.vo.request.WebSocketRequestMessage;
import com.naidelii.chat.ws.service.WebSocketRequestMessageStrategyHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author naidelii
 */
@Slf4j
public class UnknownTypeHandler implements WebSocketRequestMessageStrategyHandler {
    @Override
    public void handle(WebSocketRequestMessage message) {
        // 对未知消息类型只记录日志，不进行处理
        log.warn("Unknown Message: {}", message);
    }

    @Override
    public WebSocketRequestTypeEnum type() {
        return WebSocketRequestTypeEnum.UNKNOWN;
    }
}
