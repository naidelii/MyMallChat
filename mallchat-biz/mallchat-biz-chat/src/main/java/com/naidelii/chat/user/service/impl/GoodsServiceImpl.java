package com.naidelii.chat.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.Goods;
import com.naidelii.chat.user.mapper.GoodsMapper;
import com.naidelii.chat.user.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

}
