package com.naidelii.chat.user.controller;

import com.naidelii.chat.user.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author naidelii
 */
@RequestMapping("/sysUser")
@RestController
@Slf4j
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService userService;


}
