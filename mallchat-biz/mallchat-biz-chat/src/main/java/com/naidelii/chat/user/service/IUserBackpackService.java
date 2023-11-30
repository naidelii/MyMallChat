package com.naidelii.chat.user.service;


import com.naidelii.chat.user.domain.entity.UserBackpack;

/**
 * 背包表
 *
 * @author naidelii
 * @date 2023-11-29 16:47:16
 */
public interface IUserBackpackService {

    /**
     * 查询背包里面物品的数量
     *
     * @param userId  用户id
     * @param goodsId 物品id
     * @return 数量
     */
    Integer getCountByValidItemId(String userId, String goodsId);

    /**
     * 获取背包里面的物品
     *
     * @param userId  用户id
     * @param goodsId 物品id
     * @return 物品
     */
    UserBackpack getFirstValidItem(String userId, String goodsId);

    /**
     * 使用背包里面的物品
     *
     * @param itemId 背包里面的物品id
     * @return 更新结果
     */
    boolean useItem(String itemId);
}

