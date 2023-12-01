package com.naidelii.chat.user.service;

/**
 * 背包表
 *
 * @author naidelii
 * @date 2023-11-29 16:47:16
 */
public interface IUserBackpackService {

    /**
     * 物品发放
     *
     * @param userId     用户id
     * @param itemId     物品id
     * @param type       发放场景
     * @param businessId 业务id
     */
    void distributeItem(String userId, String itemId, Integer type, String businessId);

}

