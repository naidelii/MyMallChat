package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.SysRole;
import com.naidelii.chat.user.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author naidelii
 */
@Service
public class SysRoleDao extends ServiceImpl<SysRoleMapper, SysRole> {

    public Set<String> getRolesByUserId(String userId) {
        return baseMapper.getRolesByUserId(userId);
    }
}
