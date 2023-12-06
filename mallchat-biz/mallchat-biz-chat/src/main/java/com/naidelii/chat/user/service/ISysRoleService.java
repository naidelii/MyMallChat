package com.naidelii.chat.user.service;

import com.naidelii.chat.user.domain.entity.SysRole;

import java.util.Set;

/**
 * 角色表
 *
 * @author naidelii
 * @date 2023-12-05 09:35:52
 */
public interface ISysRoleService {

    /**
     * 新增角色
     *
     * @param role 角色
     */
    void add(SysRole role);


    /**
     * 查询用户的所有角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    Set<String> getRolesByUserId(String userId);
}

