package com.naidelii.web;

import com.naidelii.chat.user.domain.entity.SysRole;
import com.naidelii.chat.user.domain.entity.SysUserRole;
import com.naidelii.chat.user.domain.enums.RoleEnum;
import com.naidelii.chat.user.service.ISysRoleService;
import com.naidelii.chat.user.service.ISysUserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class SysRoleTest {
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysUserRoleService userRoleService;

    @Test
    public void add() {
        SysRole role = new SysRole();
        role.setRoleName("群聊管理员");
        role.setRoleCode("manager");
        role.setId("2");
//        roleService.add(role);
    }

    @Test
    public void addUserRole() {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId("1731592130360537090");
        sysUserRole.setRoleId(RoleEnum.ADMIN.getId());
//        userRoleService.add(sysUserRole);
//        roleService.add(role);
    }

    @Test
    public void queryByUserId() {
        Set<String> rolesByUserId = roleService.getRolesByUserId("1731592130360537090");
        System.out.println(rolesByUserId);
    }
}
