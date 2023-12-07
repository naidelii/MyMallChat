package com.naidelii.chat.user.service.impl;

import com.naidelii.chat.common.event.UserBlackEvent;
import com.naidelii.chat.user.dao.BlackDao;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.domain.entity.Black;
import com.naidelii.chat.user.domain.entity.IpInfo;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.enums.BlackTypeEnum;
import com.naidelii.chat.user.service.IBlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author naidelii
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BlackServiceImpl implements IBlackService {

    private final BlackDao blackDao;
    private final SysUserDao userDao;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void blockUserByUserId(String userId) {
        // 查询出用户信息
        SysUser user = userDao.getById(userId);
        if (user == null) {
            log.warn("拉黑用户不存在：{}", userId);
            return;
        }
        // 拉黑用户id
        Black black = new Black();
        black.setType(BlackTypeEnum.USER_ID.getType());
        black.setTarget(userId);
        blackDao.save(black);
        // 查询用户的ip同时拉黑
        IpInfo ipInfo = user.getIpInfo();
        if (Objects.nonNull(ipInfo)) {
            // 如果ip相同，只拉黑一个
            blockIp(ipInfo.getUpdateIp());
            if (!Objects.equals(ipInfo.getCreateIp(), ipInfo.getUpdateIp())) {
                blockIp(ipInfo.getCreateIp());
            }
        }
        // 推送拉黑事件
        UserBlackEvent event = new UserBlackEvent(this, user);
        publisher.publishEvent(event);
    }

    @Override
    public void blockIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            log.warn("拉黑的ip不存在：{}", ip);
            return;
        }
        Black black = new Black();
        black.setType(BlackTypeEnum.IP.getType());
        black.setTarget(ip);
        blackDao.save(black);
    }
}