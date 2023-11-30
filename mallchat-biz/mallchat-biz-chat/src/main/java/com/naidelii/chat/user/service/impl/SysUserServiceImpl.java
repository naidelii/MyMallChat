package com.naidelii.chat.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.base.exception.MallChatException;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.domain.enums.ItemEnum;
import com.naidelii.chat.user.domain.vo.request.ModifyNameRequest;
import com.naidelii.chat.user.domain.vo.response.UserInfoResponse;
import com.naidelii.chat.user.mapper.SysUserMapper;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.user.service.IUserBackpackService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final IUserBackpackService backpackService;

    @Override
    public SysUser getByOpenId(String openId) {
        LambdaQueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>().lambda().eq(SysUser::getOpenId, openId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(SysUser sysUser) {
        baseMapper.insert(sysUser);
    }

    @Override
    public UserInfoResponse getUserInfo(String userId) {
        // 查询用户信息
        SysUser sysUser = baseMapper.selectById(userId);
        // 获取背包中该用户的改名卡剩余次数
        Integer count = backpackService.getCountByValidItemId(userId, ItemEnum.MODIFY_NAME_CARD.getId());
        // 查询剩余改名次数
        return UserAdapter.buildUserInfo(sysUser, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(String userId, ModifyNameRequest updateData) {
        // 昵称
        String nickname = updateData.getNickname();
        // 根据昵称查询用户是否已经存在
        SysUser dbUser = getByName(nickname);
        if (dbUser != null) {
            throw new MallChatException("该昵称已存在！请换一个吧");
        }
        // 获取背包里面的改名卡
        UserBackpack backpack = backpackService.getFirstValidItem(userId, ItemEnum.MODIFY_NAME_CARD.getId());
        if (backpack == null) {
            throw new MallChatException("改名卡不够了");
        }
        // 使用改名卡
        boolean flag = backpackService.useItem(backpack.getId());
        if (flag) {
            // 改名
            SysUser user = new SysUser();
            user.setId(userId);
            user.setNickname(nickname);
            baseMapper.updateById(user);
        }
    }

    /**
     * 根据昵称获取用户
     *
     * @param nickname 昵称
     * @return 用户
     */
    private SysUser getByName(String nickname) {
        return lambdaQuery()
                .eq(SysUser::getNickname, nickname)
                .one();
    }


}
