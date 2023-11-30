package com.naidelii.chat.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Service
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
}
