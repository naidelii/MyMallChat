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
     * 登录成功返回
     */
    LOGIN_SUCCESS(2, LoginSuccess.class);

    private final Integer type;
    private final Class<?> dataClass;

}
