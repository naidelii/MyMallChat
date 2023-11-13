package com.naidelii.chat.ws.service.impl;

import com.naidelii.chat.ws.domain.dto.WebSocketChannelExtraDto;
import com.naidelii.chat.ws.service.IWebSocketService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author naidelii
 */
@Component
@Slf4j
public class WebSocketServiceImpl implements IWebSocketService {

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

}
