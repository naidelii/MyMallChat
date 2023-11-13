package com.naidelii.chat.ws.service;

import io.netty.channel.Channel;

/**
 * @author naidelii
 */
public interface IWebSocketService {


    /**
     * 客户端上线
     * @param channel channel
     */
    void online(Channel channel);

    /**
     * 客户端下线
     * @param channel channel
     */
    void offline(Channel channel);
}
