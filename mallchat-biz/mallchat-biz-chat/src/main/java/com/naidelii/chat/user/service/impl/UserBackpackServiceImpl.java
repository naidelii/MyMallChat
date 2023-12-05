package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.user.dao.ItemDao;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.dao.UserBackpackDao;
import com.naidelii.chat.user.domain.entity.Item;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.domain.enums.ItemTypeEnum;
import com.naidelii.chat.user.domain.vo.response.BadgeResponse;
import com.naidelii.chat.user.service.IUserBackpackService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
import com.naidelii.chat.user.service.adapter.UserBackpackAdapter;
import com.naidelii.data.annotation.RedissonLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author naidelii
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserBackpackServiceImpl implements IUserBackpackService {
    private final UserBackpackDao backpackDao;
    private final ItemDao itemDao;
    private final SysUserDao userDao;

    @Lazy
    @Autowired
    private UserBackpackServiceImpl backpackService;

    @Override
    public List<BadgeResponse> badgeList(String userId) {
        // 1.查询出所有的徽章
        List<Item> goodsList = itemDao.getByType(ItemTypeEnum.BADGE.getType());
        // 2.查询我所拥有的徽章（背包）
        Set<String> goodsIds = goodsList
                .stream()
                .map(Item::getId)
                .collect(Collectors.toSet());
        List<UserBackpack> backpackList = backpackDao.getByGoodsIds(goodsIds, userId);
        // 3.查询我所佩戴的徽章信息
        SysUser user = userDao.getById(userId);
        String itemId = user.getItemId();
        return UserAdapter.buildBadgeResponse(goodsList, backpackList, itemId);
    }

    @Override
    public void distributeItem(String userId, String itemId, Integer type, String businessId) {
        // 生成幂等号
        String idempotent = generateIdempotent(itemId, type, businessId);
        backpackService.doDistributeItem(idempotent, userId, itemId);
    }

    @RedissonLock(key = "#idempotent", waitTime = 5)
    public void doDistributeItem(String idempotent, String userId, String itemId) {
        // 首先查询幂等号是否已经存在了
        UserBackpack dbUserBackpack = backpackDao.getByIdempotent(idempotent);
        if (Objects.isNull(dbUserBackpack)) {
            // 执行业务，发放物品
            UserBackpack userBackpack = UserBackpackAdapter.buildDistributeItem(userId, itemId, idempotent);
            backpackDao.save(userBackpack);
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