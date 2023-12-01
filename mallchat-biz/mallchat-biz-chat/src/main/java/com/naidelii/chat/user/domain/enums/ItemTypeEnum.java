package com.naidelii.chat.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author naidelii
 * 物品类型枚举
 */
@AllArgsConstructor
@Getter
public enum ItemTypeEnum {

    /**
     * 道具
     */
    TOOL(1, "道具"),
    /**
     * 徽章
     */
    BADGE(2, "徽章"),
    ;

    private final Integer type;
    private final String desc;

    private static final Map<Integer, ItemTypeEnum> CACHE;

    static {
        CACHE = Arrays.stream(ItemTypeEnum.values()).collect(Collectors.toMap(ItemTypeEnum::getType, Function.identity()));
    }

    public static ItemTypeEnum of(Integer type) {
        return CACHE.get(type);
    }
}
