package com.naidelii.chat.ws.service.handler;

import com.naidelii.chat.ws.domain.enums.RequestTypeEnum;
import com.naidelii.chat.ws.service.RequestMessageStrategyHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author naidelii
 */
@Slf4j
public class UnknownTypeHandler implements RequestMessageStrategyHandler {
    @Override
    public RequestTypeEnum type() {
        return RequestTypeEnum.UNKNOWN;
    }

    @Override
    public void handleMessage(String data, Channel channel) {
        // 对未知消息类型只记录日志，不进行处理
        log.warn("Unknown Message: {}", data);
    }
}
