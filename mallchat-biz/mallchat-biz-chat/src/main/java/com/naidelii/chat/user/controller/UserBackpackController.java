package com.naidelii.chat.user.controller;

import com.naidelii.base.domain.vo.response.Result;
import com.naidelii.chat.user.domain.vo.response.BadgeResponse;
import com.naidelii.chat.user.service.IUserBackpackService;
import com.naidelii.security.entity.LoginUser;
import com.naidelii.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 背包表
 *
 * @author naidelii
 * @date 2023-11-29 16:47:16
 */
@RequestMapping("/userBackpack")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "背包表")
public class UserBackpackController {

    private final IUserBackpackService backpackService;

    @GetMapping("/badgeList")
    @ApiOperation("可选徽章预览")
    public Result<List<BadgeResponse>> badgeList() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<BadgeResponse> list = backpackService.badgeList(loginUser.getId());
        return Result.success(list);
    }

}
