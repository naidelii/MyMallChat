package com.naidelii.chat.user.domain.vo.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author naidelii
 */
@Data
public class ModifyNameRequest {

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空！")
    @Length(max = 10, message = "昵称不能取太长！")
    private String nickname;
}
