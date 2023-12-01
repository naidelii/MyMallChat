package com.naidelii.chat.user.service.impl;

import com.naidelii.base.exception.MallChatException;
import com.naidelii.chat.user.dao.UserBackpackDao;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.service.IUserBackpackService;
import com.naidelii.chat.user.service.adapter.UserBackpackAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author naidelii
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserBackpackServiceImpl implements IUserBackpackService {
    private final RedissonClient redissonClient;
    private final UserBackpackDao backpackDao;

    @Override
    public void distributeItem(String userId, String itemId, Integer type, String businessId) {
        // 生成幂等号
        String idempotent = generateIdempotent(itemId, type, businessId);
        // 获取锁对象
        RLock lock = redissonClient.getLock("distributeItem:" + idempotent);
        // 获取锁
        boolean isLock = lock.tryLock();
        // 判断锁获取释放成功
        if (!isLock) {
            throw new MallChatException("请求太频繁了！");
        }
        try {
            // 首先查询幂等号是否已经存在了
            UserBackpack dbUserBackpack = backpackDao.getByIdempotent(idempotent);
            if (Objects.isNull(dbUserBackpack)) {
                // 执行业务，发放物品
                UserBackpack userBackpack = UserBackpackAdapter.buildDistributeItem(userId, itemId, idempotent);
                backpackDao.save(userBackpack);
            }
        } finally {
            // 解锁
            lock.unlock();
        }

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