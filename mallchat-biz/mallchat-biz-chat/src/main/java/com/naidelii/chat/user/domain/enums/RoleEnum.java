package com.naidelii.chat.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 超级管理员
     */
    ADMIN("1", "超级管理员"),
    /**
     * 群聊管理员
     */
    MANAGER("2", "群聊管理员"),
    ;
    private final String id;
    private final String roleCode;
}
