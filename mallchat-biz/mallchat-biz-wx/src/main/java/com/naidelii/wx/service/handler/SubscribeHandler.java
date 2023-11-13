package com.naidelii.wx.service.handler;

import cn.hutool.core.util.StrUtil;
import com.naidelii.wx.service.adapter.MessageTextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Component
@Slf4j
public class SubscribeHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        log.info("新关注用户 OPENID: " + wxMessage.getFromUser());
        // 二维码携带的参数
        String eventKey = wxMessage.getEventKey();
        // 通过扫码关注的
        if (StrUtil.isNotBlank(eventKey)) {
            // 新关注的用户
            String alreadyConcernedPrefix = "qrscene_";
            // 新关注的用户扫码
            if (eventKey.startsWith(alreadyConcernedPrefix)) {
                // 截取掉前部分不要
                eventKey = eventKey.substring(0, alreadyConcernedPrefix.length());
            }
        }
        System.out.println("eventKey：" + eventKey);
        // 回复消息
        String content = "感谢关注";
        return new MessageTextBuilder().build(content, wxMessage);
    }

}
