package com.naidelii.chat.wx.service;


/**
 * @author naidelii
 */
public interface IWeChatService {

    /**
     * 生成二维码
     *
     * @param code 场景值ID
     * @return 二维码链接
     */
    String generateQRCode(String code);


}
