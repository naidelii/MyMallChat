package com.naidelii.chat.wx.service.handler;

import com.naidelii.chat.wx.config.WeChatOtherProperties;
import com.naidelii.constant.CommonConstants;
import com.naidelii.exception.MallChatException;
import com.naidelii.wx.config.WeChatProperties;
import com.naidelii.wx.service.adapter.MessageTextBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WeChatHandler {

    /**
     * 用户的openId和前端登录场景code的映射关系
     */
    private static final String AUTHORIZATION_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    private final WeChatProperties weChatProperties;
    private final WeChatOtherProperties weChatOtherProperties;

    /**
     * 获取授权链接
     *
     * @param wxMessage wxMessage
     * @return WxMpXmlOutMessage
     */
    public WxMpXmlOutMessage getAuthorizationLink(WxMpXmlMessage wxMessage) {
        // 微信用户的openId
        String openId = wxMessage.getFromUser();
        // 前端登录场景code
        String loginCode = getEventKey(wxMessage);
        // 根据openId查询用户是否存在

        // 回调地址
        String callBackUrl;
        try {
            String callBack = weChatOtherProperties.getDomain() + weChatOtherProperties.getCallback();
            callBackUrl = URLEncoder.encode(callBack, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("授权链接编码失败", e);
            throw new MallChatException("授权链接编码失败!");
        }
        String skipUrl = String.format(AUTHORIZATION_URL, weChatProperties.getAppId(), callBackUrl);
        // 回复消息
        String content = "请点击链接授权：<a href=\"" + skipUrl + "\">登录</a>";
        return new MessageTextBuilder().build(content, wxMessage);
    }

    public String getEventKey(WxMpXmlMessage wxMessage) {
        // 处理事件码
        String eventKey = wxMessage.getEventKey();
        if (eventKey.startsWith(CommonConstants.QR_SCENE_PREFIX)) {
            eventKey = eventKey.replace(CommonConstants.QR_SCENE_PREFIX, "");
        }
        return eventKey;
    }
}
