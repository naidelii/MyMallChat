package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.SysUserRole;
import com.naidelii.chat.user.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author naidelii
 */
@Service
public class SysUserRoleDao extends ServiceImpl<SysUserRoleMapper, SysUserRole> {


    public List<SysUserRole> getRolesByUserId(String userId) {
        return lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
                .list();
    }
}
