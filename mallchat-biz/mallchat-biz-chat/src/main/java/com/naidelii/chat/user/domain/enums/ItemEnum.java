package com.naidelii.chat.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author naidelii
 * 物品枚举
 */
@AllArgsConstructor
@Getter
public enum ItemEnum {

    /**
     * 改名卡
     */
    MODIFY_NAME_CARD("1", ItemTypeEnum.MODIFY_NAME_CARD, "改名卡"),
    /**
     * 前十注册徽章
     */
    LIKE_BADGE("2", ItemTypeEnum.BADGE, "前十注册徽章"),
    ;

    private final String id;
    private final ItemTypeEnum typeEnum;
    private final String desc;

    private static final Map<String, ItemEnum> CACHE;

    static {
        CACHE = Arrays.stream(ItemEnum.values()).collect(Collectors.toMap(ItemEnum::getId, Function.identity()));
    }

    public static ItemEnum of(String id) {
        return CACHE.get(id);
    }

}
