package com.naidelii.chat.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.naidelii.chat.user.domain.entity.SysUser;

/**
 * @author naidelii
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据openId查询用户
     *
     * @param openId 微信openId
     * @return 用户信息
     */
    SysUser getByOpenId(String openId);

    /**
     * 注册用户
     *
     * @param sysUser 用户信息
     */
    void register(SysUser sysUser);
}
