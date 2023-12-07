package com.naidelii.chat.user.service;

/**
 * 黑名单
 *
 * @author naidelii
 * @date 2023-12-05 09:36:27
 */
public interface IBlackService {

    /**
     * 根据用户id拉黑用户
     *
     * @param userId 用户id
     */
    void blockUserByUserId(String userId);

    /**
     * 拉黑ip地址
     *
     * @param ip ip地址
     */
    void blockIp(String ip);

}

