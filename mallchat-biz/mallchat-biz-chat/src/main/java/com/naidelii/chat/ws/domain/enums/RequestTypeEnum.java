package com.naidelii.chat.ws.domain.enums;

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
public enum RequestTypeEnum {

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
    AUTHORIZE(3),

    /**
     * 未知消息
     */
    UNKNOWN(-1);

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 缓存到内存中
     */
    private static final Map<Integer, RequestTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(RequestTypeEnum.values()).collect(Collectors.toMap(RequestTypeEnum::getType, Function.identity()));
    }

    public static RequestTypeEnum of(Integer type) {
        return CACHE.get(type);
    }

}
