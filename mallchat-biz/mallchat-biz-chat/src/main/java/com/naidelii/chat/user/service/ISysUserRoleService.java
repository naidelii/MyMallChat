package com.naidelii.chat.user.service;


import com.naidelii.chat.user.domain.entity.SysUserRole;

/**
 * 用户角色关联表
 *
 * @author naidelii
 * @date 2023-12-05 09:36:17
 */
public interface ISysUserRoleService {

    /**
     * 添加用户与角色关联的数据
     *
     * @param data 数据
     */
    void add(SysUserRole data);
}

