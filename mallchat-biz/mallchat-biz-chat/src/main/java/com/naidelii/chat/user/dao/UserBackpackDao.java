package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.base.constant.enums.YesOrNoEnum;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.mapper.UserBackpackMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author naidelii
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    public Integer getCountByValidItemId(String userId, String goodsId) {
        return lambdaQuery()
                .eq(UserBackpack::getUserId, userId)
                .eq(UserBackpack::getItemId, goodsId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .count();
    }

    public UserBackpack getFirstValidItem(String userId, String itemId) {
        return lambdaQuery()
                .eq(UserBackpack::getUserId, userId)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .orderByAsc(UserBackpack::getCreateTime)
                .last("limit 1")
                .one();
    }

    public boolean invalidItem(String itemId) {
        UserBackpack userBackpack = new UserBackpack();
        userBackpack.setId(itemId);
        userBackpack.setStatus(YesOrNoEnum.YES.getStatus());
        return updateById(userBackpack);
    }

    public List<UserBackpack> getByGoodsIds(Set<String> itemId, String userId) {
        return lambdaQuery()
                .eq(UserBackpack::getUserId, userId)
                .in(UserBackpack::getItemId, itemId)
                .list();
    }

    public UserBackpack getByIdempotent(String idempotent) {
        return lambdaQuery()
                .eq(UserBackpack::getIdempotent, idempotent)
                .one();
    }
}
