package com.naidelii.websocket.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author naidelii
 */
@AllArgsConstructor
@Getter
public enum WebSocketRequestTypeEnum {

    /**
     * 登录请求时，返回认证的二维码
     */
    LOGIN(1),

    /**
     * 心跳包
     */
    HEARTBEAT(2),

    /**
     * 登录认证
     */
    AUTHORIZE(3);

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 缓存到内存中
     */
    private static final Map<Integer, WebSocketRequestTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(WebSocketRequestTypeEnum.values()).collect(Collectors.toMap(WebSocketRequestTypeEnum::getType, Function.identity()));
    }

    public static WebSocketRequestTypeEnum of(Integer type) {
        return CACHE.get(type);
    }

}
