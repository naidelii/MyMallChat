package com.naidelii.chat.user.domain.vo.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author naidelii
 */
@Data
public class SysRoleRequest {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空！")
    private String roleName;
    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空！")
    private String roleCode;
}
