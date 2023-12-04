package com.naidelii.chat.common.event.listener;

import com.naidelii.chat.common.event.UserRegisterEvent;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.domain.enums.ItemDistributionSceneEnum;
import com.naidelii.chat.user.domain.enums.ItemEnum;
import com.naidelii.chat.user.service.IUserBackpackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author naidelii
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisterListener {

    private final IUserBackpackService userBackpackService;
    private final SysUserDao userDao;


    /**
     * 发送改名卡
     * TransactionalEventListener：被注解的方法可以在事务的不同阶段去触发执行
     * AFTER_COMMIT - 默认设置，在事务提交后执行
     *
     * @param event 用户注册事件
     */
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendCard(UserRegisterEvent event) {
        // 用户信息
        SysUser user = event.getUser();
        String userId = user.getId();
        userBackpackService.distributeItem(userId, ItemEnum.MODIFY_NAME_CARD.getId(), ItemDistributionSceneEnum.USER_ID.getType(), userId);
    }

    /**
     * 发送徽章
     * TransactionalEventListener：被注解的方法可以在事务的不同阶段去触发执行
     * AFTER_COMMIT - 默认设置，在事务提交后执行
     *
     * @param event 用户注册事件
     */
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendBadge(UserRegisterEvent event) {
        // 获取用户注册的人数（性能瓶颈，等注册用户多了直接删掉）
        int count = userDao.count();
        // 目前只有前十注册徽章和前100徽章
        if (count > 100) {
            return;
        }
        // 用户信息
        SysUser user = event.getUser();
        String userId = user.getId();
        // 前十注册徽章
        if (count <= 10) {
            distributeBadge(userId, ItemEnum.REG_TOP10_BADGE);
        } else if (count <= 100) {
            distributeBadge(userId, ItemEnum.REG_TOP100_BADGE);
        }
    }

    private void distributeBadge(String userId, ItemEnum badgeItem) {
        userBackpackService.distributeItem(userId, badgeItem.getId(), ItemDistributionSceneEnum.USER_ID.getType(), userId);
    }

}
