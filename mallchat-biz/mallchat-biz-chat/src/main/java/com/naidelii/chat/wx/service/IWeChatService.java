package com.naidelii.chat.wx.service;


import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

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

    /**
     * 扫码事件处理
     *
     * @param wxMessage wxMessage
     * @return WxMpXmlOutMessage
     */
    WxMpXmlOutMessage scan(WxMpXmlMessage wxMessage);

    /**
     * 用户授权
     * @param userInfo 用户信息
     */
    void authorization(WxOAuth2UserInfo userInfo);
}
