package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.user.dao.UserBackpackDao;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.service.IUserBackpackService;
import com.naidelii.chat.user.service.adapter.UserBackpackAdapter;
import com.naidelii.data.service.LockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author naidelii
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserBackpackServiceImpl implements IUserBackpackService {
    private final UserBackpackDao backpackDao;
    private final LockService lockService;

    @Override
    public void distributeItem(String userId, String itemId, Integer type, String businessId) {
        // 生成幂等号
        String idempotent = generateIdempotent(itemId, type, businessId);
        // key
        String key = "distributeItem:" + idempotent;
        lockService.executeWithLockThrows(key, () -> {
            // 首先查询幂等号是否已经存在了
            UserBackpack dbUserBackpack = backpackDao.getByIdempotent(idempotent);
            if (Objects.isNull(dbUserBackpack)) {
                // 执行业务，发放物品
                UserBackpack userBackpack = UserBackpackAdapter.buildDistributeItem(userId, itemId, idempotent);
                backpackDao.save(userBackpack);
            }
        });
    }

    /**
     * 生成幂等号
     *
     * @param itemId     物品id
     * @param type       业务类型
     * @param businessId 业务id
     * @return 幂等号
     */
    private String generateIdempotent(String itemId, Integer type, String businessId) {
        // 模板：业务类型-业务id-物品id
        String template = "%d:%s:%s";
        return String.format(template, type, businessId, itemId);
    }
}