package com.naidelii.chat.common.event.listener;

import com.naidelii.chat.common.event.UserBlackEvent;
import com.naidelii.chat.user.dao.SysUserDao;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.cache.BlackCache;
import com.naidelii.chat.ws.domain.vo.response.BlackUser;
import com.naidelii.chat.ws.domain.vo.response.ResponseMessage;
import com.naidelii.chat.ws.service.IWebSocketService;
import com.naidelii.chat.ws.service.adapter.MessageAdapter;
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
public class UserBlackListener {

    private final SysUserDao userDao;
    private final IWebSocketService webSocketService;
    private final BlackCache blackCache;

    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessage(UserBlackEvent event) {
        SysUser user = event.getUser();
        ResponseMessage<BlackUser> responseMessage = MessageAdapter.buildBlack(user);
        webSocketService.broadcast(responseMessage);
    }

    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void changeUserStatus(UserBlackEvent event) {
        SysUser user = event.getUser();
        userDao.invalidUserId(user.getId());
    }

    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void evictCache(UserBlackEvent event) {
        blackCache.emptyBlackMap();
    }

}
