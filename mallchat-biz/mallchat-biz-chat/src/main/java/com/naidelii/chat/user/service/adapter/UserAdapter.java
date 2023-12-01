package com.naidelii.chat.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.base.constant.enums.YesOrNoEnum;
import com.naidelii.chat.user.domain.entity.Item;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.entity.UserBackpack;
import com.naidelii.chat.user.domain.vo.response.BadgeResponse;
import com.naidelii.chat.user.domain.vo.response.UserInfoResponse;
import com.naidelii.security.entity.LoginUser;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.*;
import java.util.stream.Collectors;

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

    public static UserInfoResponse buildUserInfo(SysUser sysUser, Integer count) {
        UserInfoResponse vo = new UserInfoResponse();
        BeanUtil.copyProperties(sysUser, vo);
        vo.setRenameCount(count);
        return vo;
    }

    public static List<BadgeResponse> buildBadgeResponse(List<Item> goodsList, List<UserBackpack> backpackList, String itemId) {
        // 已拥有的徽章
        Set<String> alreadyOwned = backpackList
                .stream()
                .map(UserBackpack::getItemId)
                .collect(Collectors.toSet());
        return goodsList
                .stream()
                .map(goods -> {
                    // 创建vo对象
                    BadgeResponse vo = new BadgeResponse();
                    BeanUtil.copyProperties(goods, vo);
                    // 是否拥有了该徽章
                    boolean obtainFlag = alreadyOwned.contains(goods.getId());
                    vo.setObtain(obtainFlag ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
                    // 是否佩戴了该徽章
                    boolean wearingFlag = Objects.equals(itemId, goods.getId());
                    vo.setWearing(wearingFlag ? YesOrNoEnum.YES.getStatus() : YesOrNoEnum.NO.getStatus());
                    return vo;
                })
                // 根据是否佩戴进行倒序排序
                .sorted(Comparator.comparing(BadgeResponse::getWearing, Comparator.reverseOrder())
                        .thenComparing(BadgeResponse::getObtain, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
