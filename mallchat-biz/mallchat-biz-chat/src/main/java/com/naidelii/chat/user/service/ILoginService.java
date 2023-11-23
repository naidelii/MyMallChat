package com.naidelii.chat.user.service;

/**
 * @author naidelii
 */
public interface ILoginService {


    /**
     * 扫码登录
     *
     * @param userId 用户id
     * @return token
     */
    String scanQRCodeLogin(String userId);
}
