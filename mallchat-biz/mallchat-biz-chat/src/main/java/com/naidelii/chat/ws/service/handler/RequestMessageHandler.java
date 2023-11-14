package com.naidelii.chat.ws.service.handler;

import com.naidelii.chat.ws.domain.enums.RequestTypeEnum;
import com.naidelii.chat.ws.service.RequestMessageStrategyHandler;
import com.naidelii.chat.ws.service.factory.WebSocketRequestHandlerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestMessageHandler {

    private final WebSocketRequestHandlerFactory webSocketRequestHandlerFactory;

    public RequestMessageStrategyHandler getHandlerByType(Integer type) {
        // 获取到对应的类型
        RequestTypeEnum requestTypeEnum = RequestTypeEnum.of(type);
        // 根据类型选择合适的处理器
        return webSocketRequestHandlerFactory.getHandlerByType(requestTypeEnum);
    }

}
