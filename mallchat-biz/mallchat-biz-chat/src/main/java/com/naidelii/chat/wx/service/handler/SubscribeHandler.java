package com.naidelii.chat.wx.service.handler;

import cn.hutool.core.util.StrUtil;
import com.naidelii.chat.wx.service.IWeChatService;
import com.naidelii.wx.service.adapter.MessageTextBuilder;
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
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * 关注事件
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SubscribeHandler extends AbstractHandler {

    private final IWeChatService weChatService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());
        String event = wxMessage.getEventKey();
        // 通过扫码的方式进行关注
        if (!StrUtil.isBlank(event)) {
            return weChatService.scan(wxMessage);
        }
        // 回复默认消息（不是通过扫码进行关注的）
        String content = "感谢关注";
        return new MessageTextBuilder().build(content, wxMessage);
    }

}
