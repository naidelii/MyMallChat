package com.naidelii.base.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 * 是/否
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {

    /**
     * 否
     */
    NO(0, "否"),

    /**
     * 是
     */
    YES(1, "是");

    private final Integer status;
    private final String desc;
}
