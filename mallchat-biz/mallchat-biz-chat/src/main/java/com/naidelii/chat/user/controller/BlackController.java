package com.naidelii.chat.user.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
