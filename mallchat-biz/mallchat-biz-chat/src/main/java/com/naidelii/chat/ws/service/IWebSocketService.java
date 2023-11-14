package com.naidelii.chat.ws.service;

import com.naidelii.chat.ws.domain.vo.response.ResponseMessage;
import io.netty.channel.Channel;

/**
 * @author naidelii
 */
public interface IWebSocketService {

    /**
     * 客户端上线
     *
     * @param channel channel
     */
    void online(Channel channel);

    /**
     * 客户端下线
     *
     * @param channel channel
     */
    void offline(Channel channel);


    /**
     * 发送消息
     *
     * @param channel channel
     * @param message message
     */
    void sendMessage(Channel channel, ResponseMessage<?> message);
}
