package com.naidelii.wx.config;

import com.naidelii.wx.service.handler.MsgHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * wechat mp configuration
 *
 * @author <a href="https://github.com/binarywang">binary wang</a>
 */
@Configuration
@EnableConfigurationProperties(WeChatProperties.class)
@RequiredArgsConstructor
@Slf4j
public class WeChatConfiguration {

    private final WeChatProperties properties;
    private final MsgHandler msgHandler;

    @Bean
    public WxMpService wxMpService() {
        WxMpService service = new WxMpServiceImpl();
        final WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        // 设置微信公众号的appId
        config.setAppId(properties.getAppId());
        // 设置微信公众号的app corpSecret
        config.setSecret(properties.getSecret());
        // 设置微信公众号的token
        config.setToken(properties.getToken());
        // 设置消息加解密密钥
        config.setAesKey(properties.getAesKey());
        service.setWxMpConfigStorage(config);
        return service;
    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 关注事件
//        router.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

//        // 扫码事件
//        router.rule().async(false).msgType(EVENT).event(WxConsts.EventType.SCAN).handler(this.scanHandler).end();
        // 默认
        router.rule().async(false).handler(msgHandler).end();
        return router;
    }

}