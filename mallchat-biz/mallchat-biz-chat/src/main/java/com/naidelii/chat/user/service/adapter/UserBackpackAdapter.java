package com.naidelii.chat.user.service.adapter;

import com.naidelii.chat.user.domain.entity.UserBackpack;

/**
 * @author naidelii
 */
public class UserBackpackAdapter {
    public static UserBackpack buildDistributeItem(String userId, String itemId, String idempotent) {
        UserBackpack backpack = new UserBackpack();
        backpack.setUserId(userId);
        backpack.setItemId(itemId);
        backpack.setIdempotent(idempotent);
        return backpack;
    }
}
