package com.naidelii.chat.user.service;

/**
 * @author naidelii
 */
public interface IIpService {

    /**
     * 根据userId解析ip信息
     *
     * @param userId 用户id
     */
    void asyncParseByUserId(String userId);
}
