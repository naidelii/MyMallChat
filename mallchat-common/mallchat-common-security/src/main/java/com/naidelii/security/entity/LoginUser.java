package com.naidelii.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author naidelii
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 性别 1男，2女
     */
    private Integer sex;

    /**
     * 客户端的ip
     */
    private String clientIp;

}
