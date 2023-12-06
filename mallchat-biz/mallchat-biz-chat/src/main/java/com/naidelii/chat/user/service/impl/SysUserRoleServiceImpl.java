package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.user.dao.SysUserRoleDao;
import com.naidelii.chat.user.domain.entity.SysUserRole;
import com.naidelii.chat.user.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserRoleServiceImpl implements ISysUserRoleService {
    private final SysUserRoleDao userRoleDao;

    @Override
    public void add(SysUserRole data) {
        userRoleDao.save(data);
    }
}