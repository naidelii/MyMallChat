package com.naidelii.chat.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.naidelii.base.exception.MallChatException;
import com.naidelii.chat.common.event.UserRegisterEvent;
import com.naidelii.chat.user.dao.ItemDao;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.dao.UserBackpackDao;
import com.naidelii.chat.user.domain.entity.Item;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.domain.enums.ItemEnum;
import com.naidelii.chat.user.domain.enums.ItemTypeEnum;
import com.naidelii.chat.user.domain.vo.request.ModifyNameRequest;
import com.naidelii.chat.user.domain.vo.response.UserInfoResponse;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements ISysUserService {

    private final SysUserDao userDao;
    private final UserBackpackDao backpackDao;
    private final ItemDao itemDao;
    private final ApplicationEventPublisher publisher;

    @Override
    public SysUser getByOpenId(String openId) {
        LambdaQueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>().lambda().eq(SysUser::getOpenId, openId);
        return userDao.getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(SysUser sysUser) {
        userDao.save(sysUser);
        // 发布用户注册事件
        UserRegisterEvent userRegisterEvent = new UserRegisterEvent(this, sysUser);
        publisher.publishEvent(userRegisterEvent);
    }

    @Override
    public UserInfoResponse getUserInfo(String userId) {
        // 查询用户信息
        SysUser sysUser = userDao.getById(userId);
        // 获取背包中该用户的改名卡剩余次数
        Integer count = backpackDao.getCountByValidItemId(userId, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(sysUser, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeNickname(String userId, ModifyNameRequest updateData) {
        // 昵称
        String nickname = updateData.getNickname();
        // 根据昵称查询用户是否已经存在
        SysUser dbUser = userDao.getByName(nickname);
        if (dbUser != null) {
            throw new MallChatException("该昵称已存在！请换一个吧");
        }
        // 获取背包里面的改名卡
        UserBackpack backpack = backpackDao.getFirstValidItem(userId, ItemEnum.MODIFY_NAME_CARD.getId());
        if (backpack == null) {
            throw new MallChatException("改名卡不够了");
        }
        // 使用改名卡
        boolean flag = backpackDao.invalidItem(backpack.getId());
        if (flag) {
            // 改名
            userDao.modifyName(userId, nickname);
        }
    }


    @Override
    public void wearBadge(String userId, String itemId) {
        // 1.确保有这个物品
        Item item = itemDao.getById(itemId);
        if (item == null) {
            throw new MallChatException("暂无该物品！");
        }
        // 2.确保该物品是徽章
        if (!Objects.equals(ItemTypeEnum.BADGE.getType(), item.getItemType())) {
            throw new MallChatException("只有徽章才能佩戴！");
        }
        // 3.确保背包中有该徽章
        UserBackpack backpack = backpackDao.getFirstValidItem(userId, itemId);
        if (backpack == null) {
            throw new MallChatException("该徽章暂未获得！");
        }
        // 4.佩戴
        userDao.wearBadge(userId, itemId);
    }

}
