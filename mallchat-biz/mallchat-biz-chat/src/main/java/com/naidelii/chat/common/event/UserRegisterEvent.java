package com.naidelii.chat.common.event;

import com.naidelii.chat.user.domain.entity.SysUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


/**
 * @author naidelii
 * 用户注册事件
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {

    private final SysUser user;


    public UserRegisterEvent(Object source, SysUser user) {
        super(source);
        this.user = user;
    }
}
