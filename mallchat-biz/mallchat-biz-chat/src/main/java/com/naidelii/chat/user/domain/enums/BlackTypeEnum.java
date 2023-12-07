package com.naidelii.chat.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 */

@Getter
@AllArgsConstructor
public enum BlackTypeEnum {
    /**
     * 用户id
     */
    USER_ID(1, "用户id"),
    /**
     * ip
     */
    IP(2, "ip地址"),
    ;

    private final Integer type;
    private final String desc;
}
