package com.naidelii.chat.ws.service;

import com.naidelii.chat.ws.domain.enums.RequestTypeEnum;
import io.netty.channel.Channel;

/**
 * @author naidelii
 * WebSocket 请求处理策略接口
 */
public interface RequestMessageStrategyHandler {

    /**
     * 操作类型
     *
     * @return 操作类型
     */
    RequestTypeEnum type();

    /**
     * 处理消息
     *
     * @param data    消息实体
     * @param channel 通道
     */
    void handleMessage(String data, Channel channel);

}
