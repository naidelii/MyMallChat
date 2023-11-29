package com.naidelii.chat.user.controller;

import com.naidelii.chat.user.service.IGoodsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author naidelii
 */
@RequestMapping("/goods")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "物品表")
public class GoodsController {

    private final IGoodsService goodsService;


}
