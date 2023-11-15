package com.naidelii.chat.ws.domain.vo.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author naidelii
 */
@Data
@Builder
public class LoginSuccess {
    /**
     * 用户id
     */
    private String id;
    /**
     * 头像
     */
    private String avatar;
    /**
     * token
     */
    private String token;
    /**
     * 昵称
     */
    private String nickname;
}
