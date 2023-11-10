package com.naidelii.wx.service.adapter;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class MessageTextBuilder {

    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage) {
        return WxMpXmlOutMessage
                .TEXT()
                .content(content)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();
    }
}
