package com.naidelii.chat.user.domain.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author naidelii
 */
@Data
public class BlankUserRequest {
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空！")
    private String userId;
}
