package com.naidelii.chat.user.domain.vo.response;


import lombok.Data;

/**
 * @author naidelii
 */
@Data
public class UserInfoResponse {

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
     * 上下线状态 1在线 2离线
     */
    private Integer activeStatus;

    /**
     * 剩余改名次数
     */
    private Integer renameCount;

    /**
     * 佩戴的徽章
     */
    private String itemId;

}
