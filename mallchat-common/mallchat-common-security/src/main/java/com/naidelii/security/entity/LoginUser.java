package com.naidelii.security.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author naidelii
 */
@Data
@Builder
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


}
