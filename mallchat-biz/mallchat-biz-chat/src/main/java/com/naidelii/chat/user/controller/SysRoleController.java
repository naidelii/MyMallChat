package com.naidelii.chat.user.controller;

import com.naidelii.base.domain.vo.response.Result;
import com.naidelii.chat.user.domain.entity.SysRole;
import com.naidelii.chat.user.domain.vo.request.SysRoleRequest;
import com.naidelii.chat.user.service.ISysRoleService;
import com.naidelii.chat.user.service.adapter.RoleAdapter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 角色表
 *
 * @author naidelii
 * @date 2023-12-05 09:35:52
 */
@RequestMapping("/role")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "角色表")
public class SysRoleController {
    private final ISysRoleService roleService;


    @PostMapping("/add")
    @ApiOperation("新增角色")
    public Result<?> add(@Validated @RequestBody SysRoleRequest request) {
        SysRole role = RoleAdapter.buildSaveRole(request);
        roleService.add(role);
        return Result.success();
    }

}
