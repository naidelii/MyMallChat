package com.naidelii.chat.user.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author naidelii
 */
@Data
public class WearBadgeRequest {

    /**
     * 徽章的id
     */
    @NotBlank(message = "徽章id不能为空！")
    private String itemId;
}
