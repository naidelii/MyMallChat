package com.naidelii.chat.wx.service.handler;

import com.naidelii.wx.service.handler.AbstractHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author naidelii
 * 扫码事件
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ScanHandler extends AbstractHandler {

    private final WeChatHandler weChatHandler;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage,
                                    Map<String, Object> map,
                                    WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) {
        // 扫码事件处理
        return weChatHandler.getAuthorizationLink(wxMpXmlMessage);
    }

}
