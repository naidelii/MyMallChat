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

    /**
     * 生成场景码
     *
     * @param channel channel
     * @return 场景码
     */
    String generateLoginCode(Channel channel);

    /**
     * 维护openId和用户的关联
     *
     * @param code   code
     * @param openId openId
     */
    void maintainRelationships(String code, String openId);

    /**
     * 根据openId给对应的用户推送消息
     *
     * @param openId openId
     */
    void scanLoginSuccess(String openId);
}
