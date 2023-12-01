package com.naidelii.chat.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 * 物品发放场景
 */
@Getter
@AllArgsConstructor
public enum ItemDistributionSceneEnum {

    /**
     * 通过用户id进行发放
     */
    USER_ID(1, "userId"),
    /**
     * 通过消息id进行发放
     */
    MESSAGE_ID(2, "messageId"),
    ;

    private final Integer type;
    private final String key;
}
