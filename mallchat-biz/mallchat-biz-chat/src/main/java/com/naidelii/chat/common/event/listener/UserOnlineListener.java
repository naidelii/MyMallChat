package com.naidelii.chat.common.event.listener;

import com.naidelii.chat.common.event.UserOnlineEvent;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.enums.UserActiveStatusEnum;
import com.naidelii.chat.user.service.IIpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author naidelii
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserOnlineListener {

    private final SysUserDao userDao;
    private final IIpService ipService;

    @EventListener(classes = UserOnlineEvent.class)
    public void updateUserInfo(UserOnlineEvent event) {
        SysUser user = event.getUser();
        SysUser updateUser = new SysUser();
        updateUser.setId(user.getId())
                .setIpInfo(user.getIpInfo())
                .setLastOptTime(new Date())
                .setActiveStatus(UserActiveStatusEnum.ONLINE.getStatus());
        // 更新用户信息
        userDao.updateById(updateUser);
        // 用户ip解析
        ipService.asyncParseByUserId(user.getId());
    }

}
