package com.naidelii.chat.user.controller;

import com.naidelii.base.domain.vo.response.Result;
import com.naidelii.chat.user.domain.vo.request.BlankUserRequest;
import com.naidelii.chat.user.service.IBlackService;
import com.naidelii.security.annotation.RequiresRole;
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
 * 黑名单
 *
 * @author naidelii
 * @date 2023-12-05 09:36:27
 */
@RequestMapping("/black")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "黑名单")
public class BlackController {

    private final IBlackService blackService;

    @PostMapping()
    @ApiOperation("拉黑用户")
    @RequiresRole({"admin"})
    public Result<Void> blockUser(@Validated @RequestBody BlankUserRequest request) {
        blackService.blockUserByUserId(request.getUserId());
        return Result.success();
    }

}
