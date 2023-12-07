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
     * 根据openId完成扫码登录
     *
     * @param code   登陆码
     * @param userId 用户id
     */
    void scanLoginSuccess(String code, String userId);

    /**
     * 根据openId获取登陆码
     *
     * @param openId openId
     * @return 登录码
     */
    String getWaitAuthorizeCode(String openId);

    /**
     * 等待授权
     *
     * @param code 登陆码
     */
    void waitAuthorize(String code);

    /**
     * 身份认证
     *
     * @param token   token
     * @param channel channel
     */
    void authentication(String token, Channel channel);


    /**
     * 广播消息
     *
     * @param responseMessage 消息内容
     */
    void broadcast(ResponseMessage<?> responseMessage);
}
