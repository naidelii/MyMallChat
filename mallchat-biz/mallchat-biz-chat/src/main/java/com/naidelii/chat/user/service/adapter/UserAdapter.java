package com.naidelii.chat.user.service.adapter;

import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.security.entity.LoginUser;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.Date;

/**
 * @author naidelii
 */
public class UserAdapter {

    public static SysUser buildSaveUser(String openId) {
        SysUser user = SysUser
                .builder()
                .openId(openId)
                .lastOptTime(new Date())
                .build();
        user.setCreateBy(CommonConstants.DEFAULT_OPERATOR);
        user.setCreateTime(new Date());
        return user;
    }

    public static SysUser buildAuthorizeUser(String id, WxOAuth2UserInfo userInfo) {
        SysUser user = SysUser.builder()
                .id(id)
                .sex(userInfo.getSex())
                .nickname(userInfo.getNickname())
                .status(CommonConstants.USER_ACTIVATION_STATE)
                .avatar(userInfo.getHeadImgUrl())
                .lastOptTime(new Date())
                .build();
        user.setUpdateBy(CommonConstants.DEFAULT_OPERATOR);
        user.setUpdateTime(new Date());
        return user;
    }

    public static SysUser buildOnlineUser(String userId) {
        return SysUser.builder()
                .id(userId)
                .lastOptTime(new Date())
                .activeStatus(CommonConstants.USER_ONLINE_PRESENCE)
                .build();
    }

    public static LoginUser buildLoginUser(SysUser updateUser) {
        return LoginUser.builder()
                .id(updateUser.getId())
                .nickname(updateUser.getNickname())
                .avatar(updateUser.getAvatar())
                .build();
    }
}
