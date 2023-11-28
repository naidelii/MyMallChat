package com.naidelii.chat.ws.service.handler;

import com.naidelii.chat.ws.domain.enums.RequestTypeEnum;
import com.naidelii.chat.ws.service.IWebSocketService;
import com.naidelii.chat.ws.service.RequestMessageStrategyHandler;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author naidelii
 * 身份认证处理器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizeHandler implements RequestMessageStrategyHandler {

    private final IWebSocketService webSocketService;

    @Override
    public RequestTypeEnum type() {
        return RequestTypeEnum.AUTHORIZE;
    }

    @Override
    public void handleMessage(String token, Channel channel) {
        webSocketService.authentication(token, channel);
    }
}
