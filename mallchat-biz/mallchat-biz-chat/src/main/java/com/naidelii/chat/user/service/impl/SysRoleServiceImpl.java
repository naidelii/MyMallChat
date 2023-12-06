package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.user.dao.SysRoleDao;
import com.naidelii.chat.user.domain.entity.SysRole;
import com.naidelii.chat.user.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysRoleServiceImpl implements ISysRoleService {
    private final SysRoleDao roleDao;

    @Override
    public void add(SysRole role) {
        roleDao.save(role);
    }

    @Override
    public Set<String> getRolesByUserId(String userId) {
        return roleDao.getRolesByUserId(userId);
    }
}