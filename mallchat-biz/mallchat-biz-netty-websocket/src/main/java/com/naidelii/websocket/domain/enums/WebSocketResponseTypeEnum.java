package com.naidelii.websocket.domain.enums;

import com.naidelii.websocket.domain.vo.response.LoginUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 */
@AllArgsConstructor
@Getter
public enum WebSocketResponseTypeEnum {

    /**
     * 登录二维码返回
     */
    LOGIN_URL(1, LoginUrl.class);

    private final Integer type;
    private final Class<?> dataClass;

}
