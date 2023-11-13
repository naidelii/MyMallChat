package com.naidelii.chat.ws.service.impl;

import com.naidelii.chat.ws.domain.dto.WebSocketChannelExtraDto;
import com.naidelii.chat.ws.domain.enums.WebSocketRequestTypeEnum;
import com.naidelii.chat.ws.domain.vo.request.WebSocketRequestMessage;
import com.naidelii.chat.ws.service.IWebSocketService;
import com.naidelii.chat.ws.service.WebSocketRequestMessageStrategyHandler;
import com.naidelii.chat.ws.service.factory.WebSocketRequestHandlerFactory;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketServiceImpl implements IWebSocketService {

    private final WebSocketRequestHandlerFactory webSocketRequestHandlerFactory;

    /**
     * 在线列表
     */
    private static final ConcurrentHashMap<Channel, WebSocketChannelExtraDto> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    @Override
    public void online(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WebSocketChannelExtraDto());
    }

    @Override
    public void offline(Channel channel) {
        ONLINE_WS_MAP.remove(channel);
    }

    @Override
    public void handleMessage(WebSocketRequestMessage request) {
        // 获取到对应的类型
        WebSocketRequestTypeEnum requestTypeEnum = WebSocketRequestTypeEnum.of(request.getType());
        // 根据类型选择合适的处理策略
        WebSocketRequestMessageStrategyHandler handler = webSocketRequestHandlerFactory.getHandlerByType(requestTypeEnum);
        // 使用处理策略处理消息
        handler.handle(request);
    }
}
