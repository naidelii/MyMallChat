package com.naidelii.web;

import com.naidelii.chat.user.domain.enums.ItemDistributionSceneEnum;
import com.naidelii.chat.user.domain.enums.ItemEnum;
import com.naidelii.chat.user.service.IUserBackpackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserBackpackTest {
    @Autowired
    private IUserBackpackService backpackService;

    @Test
    public void distributeItem() {
        String userId = "1724724657689309186";
        backpackService.distributeItem(userId, ItemEnum.REG_TOP10_BADGE.getId(), ItemDistributionSceneEnum.USER_ID.getType(), userId);
    }
}
