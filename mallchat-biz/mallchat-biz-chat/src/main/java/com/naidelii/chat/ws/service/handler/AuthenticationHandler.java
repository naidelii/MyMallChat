package com.naidelii.chat.ws.service.handler;

import com.naidelii.chat.ws.domain.enums.WebSocketRequestTypeEnum;
import com.naidelii.chat.ws.domain.vo.request.WebSocketRequestMessage;
import com.naidelii.chat.ws.service.WebSocketRequestMessageStrategyHandler;
import org.springframework.stereotype.Component;

/**
 * @author naidelii
 * 处理身份验证逻辑的 WebSocket 请求处理策略实现类
 */
@Component
public class AuthenticationHandler implements WebSocketRequestMessageStrategyHandler {

    @Override
    public void handle(WebSocketRequestMessage data) {

    }

    @Override
    public WebSocketRequestTypeEnum type() {
        return WebSocketRequestTypeEnum.AUTHENTICATION;
    }

}
