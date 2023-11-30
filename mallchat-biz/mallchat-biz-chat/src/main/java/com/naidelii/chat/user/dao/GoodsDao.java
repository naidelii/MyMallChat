package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.Goods;
import com.naidelii.chat.user.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author naidelii
 */
@Service
public class GoodsDao extends ServiceImpl<GoodsMapper, Goods> {

    public List<Goods> getByType(Integer type) {
        return lambdaQuery()
                .eq(Goods::getType, type)
                .list();
    }
}
