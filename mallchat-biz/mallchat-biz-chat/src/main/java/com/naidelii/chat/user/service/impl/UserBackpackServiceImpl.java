package com.naidelii.chat.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.base.constant.enums.YesOrNoEnum;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.mapper.UserBackpackMapper;
import com.naidelii.chat.user.service.IUserBackpackService;
import org.springframework.stereotype.Service;


/**
 * @author naidelii
 */
@Service
public class UserBackpackServiceImpl extends ServiceImpl<UserBackpackMapper, UserBackpack> implements IUserBackpackService {

    @Override
    public Integer getCountByValidItemId(String userId, String goodsId) {
        return lambdaQuery()
                .eq(UserBackpack::getUserId, userId)
                .eq(UserBackpack::getGoodsId, goodsId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .count();
    }

    @Override
    public UserBackpack getFirstValidItem(String userId, String goodsId) {
        return lambdaQuery()
                .eq(UserBackpack::getUserId, userId)
                .eq(UserBackpack::getGoodsId, goodsId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .orderByAsc(UserBackpack::getCreateTime)
                .last("limit 1")
                .one();
    }

    @Override
    public boolean useItem(String itemId) {
        return lambdaUpdate().eq(UserBackpack::getId, itemId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .set(UserBackpack::getStatus, YesOrNoEnum.YES.getStatus())
                .update();
    }
}