package com.naidelii.chat.user.controller;

import com.naidelii.base.domain.vo.response.Result;
import com.naidelii.chat.user.domain.vo.request.ModifyNameRequest;
import com.naidelii.chat.user.domain.vo.request.WearBadgeRequest;
import com.naidelii.chat.user.domain.vo.response.BadgeResponse;
import com.naidelii.chat.user.domain.vo.response.UserInfoResponse;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.user.service.IUserBackpackService;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    private final IUserBackpackService backpackService;

    @GetMapping("/userInfo")
    @ApiOperation("获取用户个人信息")
    public Result<UserInfoResponse> getUserInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserInfoResponse userInfo = userService.getUserInfo(loginUser.getId());
        return Result.success(userInfo);
    }


    @PutMapping("/changeNickname")
    @ApiOperation("修改昵称")
    public Result<Void> changeNickname(@Validated @RequestBody ModifyNameRequest data) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        userService.changeNickname(loginUser.getId(), data);
        return Result.success();
    }

    @GetMapping("/badgeList")
    @ApiOperation("可选徽章预览")
    public Result<List<BadgeResponse>> badgeList() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<BadgeResponse> list = userService.badgeList(loginUser.getId());
        return Result.success(list);
    }


    @PutMapping("/wearBadge")
    @ApiOperation("佩戴徽章")
    public Result<Void> wearBadge(@Validated @RequestBody WearBadgeRequest request) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        userService.wearBadge(loginUser.getId(), request.getItemId());
        return Result.success();
    }


}
