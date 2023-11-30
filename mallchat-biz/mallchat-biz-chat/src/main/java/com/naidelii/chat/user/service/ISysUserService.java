package com.naidelii.chat.user.service;

import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.vo.request.ModifyNameRequest;
import com.naidelii.chat.user.domain.vo.response.UserInfoResponse;

/**
 * @author naidelii
 */
public interface ISysUserService {

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

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserInfoResponse getUserInfo(String userId);


    /**
     * 修改昵称
     *
     * @param userId     用户id
     * @param updateData 更新的信息
     */
    void changeNickname(String userId, ModifyNameRequest updateData);
}
