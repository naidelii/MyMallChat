package com.naidelii.chat.ws.domain.enums;

import com.naidelii.chat.ws.domain.vo.response.LoginSuccess;
import com.naidelii.chat.ws.domain.vo.response.LoginUrl;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 */
@AllArgsConstructor
@Getter
public enum ResponseTypeEnum {

    /**
     * 登录二维码返回
     */
    LOGIN_URL(1, LoginUrl.class),

    /**
     * 用户扫码成功，等待授权
     */
    LOGIN_SCAN_SUCCESS(2, null),

    /**
     * 用户登录成功，返回用户信息
     */
    LOGIN_SUCCESS(3, LoginSuccess.class),

    /**
     * token失效
     */
    INVALID_TOKEN(4, null);

    private final Integer type;
    private final Class<?> dataClass;

}
