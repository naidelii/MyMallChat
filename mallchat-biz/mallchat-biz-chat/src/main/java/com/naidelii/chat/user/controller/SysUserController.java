package com.naidelii.chat.user.controller;

import com.naidelii.chat.user.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


}
