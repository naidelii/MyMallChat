package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.mapper.SysUserMapper;
import org.springframework.stereotype.Repository;

/**
 * @author naidelii
 */
@Repository
public class SysUserDao extends ServiceImpl<SysUserMapper, SysUser> {

    /**
     * 根据昵称获取用户
     *
     * @param nickname 昵称
     * @return 用户
     */
    public SysUser getByName(String nickname) {
        return lambdaQuery()
                .eq(SysUser::getNickname, nickname)
                .one();
    }

    public void modifyName(String userId, String nickname) {
        SysUser user = new SysUser();
        user.setId(userId).setNickname(nickname);
        baseMapper.updateById(user);
    }

    public void wearBadge(String userId, String itemId) {
        SysUser user = new SysUser();
        user.setId(userId);
        user.setItemId(itemId);
        baseMapper.updateById(user);
    }

    public void invalidUserId(String userId) {
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setStatus(CommonConstants.USER_FROZEN_STATE);
        baseMapper.updateById(updateUser);
    }
}
