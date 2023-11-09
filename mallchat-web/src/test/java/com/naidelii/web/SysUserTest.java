package com.naidelii.web;

import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class SysUserTest {
    @Autowired
    private ISysUserService userService;

    @Test
    public void add() {
        SysUser sysUser = new SysUser();
        sysUser.setNickname("管理员");
        sysUser.setSex(1);
        sysUser.setLastOptTime(new Date());
        userService.save(sysUser);
    }
}
