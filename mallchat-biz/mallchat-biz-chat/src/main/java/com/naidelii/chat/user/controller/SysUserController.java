package com.naidelii.chat.user.controller;

import com.naidelii.base.domain.vo.response.Result;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author naidelii
 */
@RequestMapping("/user")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "用户表")
public class SysUserController {

    private final ISysUserService userService;

    /**
     * 获取用户个人信息
     *
     * @return Result
     */
    @GetMapping("/userInfo")
    @ApiOperation("获取用户个人信息")
    public Result<?> getUserInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = userService.getById(loginUser.getId());
        return Result.success(user);
    }


}
