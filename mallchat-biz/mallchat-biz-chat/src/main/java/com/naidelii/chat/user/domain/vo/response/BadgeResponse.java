package com.naidelii.chat.user.domain.vo.response;

import lombok.Data;

/**
 * @author naidelii
 * 徽章信息
 */
@Data
public class BadgeResponse {
    /**
     * 徽章id
     */
    private String id;

    /**
     * 图标
     */
    private String img;

    /**
     * 徽章描述
     */
    private String description;

    /**
     * 是否拥有：0否 1是
     */
    private Integer obtain;

    /**
     * 是否佩戴：0否 1是
     */
    private Integer wearing;
}
