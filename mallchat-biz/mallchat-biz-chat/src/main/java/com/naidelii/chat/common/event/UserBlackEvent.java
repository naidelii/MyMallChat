package com.naidelii.chat.common.event;

import com.naidelii.chat.user.domain.entity.SysUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author naidelii
 * 用户拉黑事件
 */
@Getter
public class UserBlackEvent extends ApplicationEvent {

    private final SysUser user;

    public UserBlackEvent(Object source, SysUser user) {
        super(source);
        this.user = user;
    }
}
