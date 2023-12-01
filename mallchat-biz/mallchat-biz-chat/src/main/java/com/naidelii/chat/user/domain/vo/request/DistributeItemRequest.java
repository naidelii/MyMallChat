package com.naidelii.chat.user.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author naidelii
 */
@Data
public class DistributeItemRequest {
    /**
     * 物品id
     */
    @NotBlank(message = "物品id不能为空！")
    private String itemId;
}
