package com.naidelii.chat.wx.config;

import com.naidelii.chat.wx.service.handler.MsgHandler;
import com.naidelii.chat.wx.service.handler.ScanHandler;
import com.naidelii.chat.wx.service.handler.SubscribeHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

/**
 * wechat mp configuration
 *
 * @author <a href="https://github.com/binarywang">binary wang</a>
 */
@Configuration
@EnableConfigurationProperties(WeChatOtherProperties.class)
@RequiredArgsConstructor
@Slf4j
public class WeChatMpConfiguration {

    private final MsgHandler msgHandler;
    private final SubscribeHandler subscribeHandler;
    private final ScanHandler scanHandler;

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 关注事件
        router.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(subscribeHandler).end();
        // 扫码事件
        router.rule().async(false).msgType(EVENT).event(WxConsts.EventType.SCAN).handler(scanHandler).end();
        // 默认
        router.rule().async(false).handler(msgHandler).end();
        return router;
    }

}