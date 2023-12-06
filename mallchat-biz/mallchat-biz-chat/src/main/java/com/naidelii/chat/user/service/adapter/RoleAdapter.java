package com.naidelii.chat.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.naidelii.chat.user.domain.entity.SysRole;
import com.naidelii.chat.user.domain.vo.request.SysRoleRequest;

/**
 * @author naidelii
 */
public class RoleAdapter {
    public static SysRole buildSaveRole(SysRoleRequest data) {
        SysRole role = new SysRole();
        BeanUtil.copyProperties(data, role);
        return role;
    }
}
