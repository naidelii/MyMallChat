package com.naidelii.chat.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 */
@AllArgsConstructor
@Getter
public enum UserActiveStatusEnum {

    /**
     * 在线
     */
    ONLINE(1),
    /**
     * 离线
     */
    OFFLINE(2),
    ;

    private final Integer status;
}
