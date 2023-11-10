package com.naidelii.wx.service;


import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author naidelii
 */
public interface IWeChatService {

    /**
     * 生成二维码
     * @return 二维码链接
     */
    String generateQRCode();

    /**
     * 处理微信的事件推送
     * @param message 消息
     * @return WxMpXmlOutMessage
     */
    WxMpXmlOutMessage route(WxMpXmlMessage message);

}
