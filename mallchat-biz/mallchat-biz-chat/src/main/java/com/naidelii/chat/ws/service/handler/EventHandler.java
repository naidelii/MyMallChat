package com.naidelii.chat.ws.service.handler;

import com.naidelii.chat.ws.domain.dto.WebSocketChannelExtraDto;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author naidelii
 * 事件处理器
 */
@Component
public class EventHandler {
    /**
     * 在线列表
     */
    private static final ConcurrentHashMap<Channel, WebSocketChannelExtraDto> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    public void online(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WebSocketChannelExtraDto());
    }

    /**
     * 用户下线处理
     *
     * @param channel channel
     */
    public void offline(Channel channel) {
        // 删除在线列表
        ONLINE_WS_MAP.remove(channel);
        // 关闭连接
        channel.close();
    }
}
