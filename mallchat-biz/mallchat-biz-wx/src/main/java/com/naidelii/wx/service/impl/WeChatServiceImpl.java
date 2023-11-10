package com.naidelii.wx.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.naidelii.wx.service.IWeChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;

/**
 * @author naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeChatServiceImpl implements IWeChatService {

    private final WxMpService wxMpService;
    private final WxMpMessageRouter messageRouter;


    @Override
    @SneakyThrows
    public String generateQRCode() {
        // 生成随机不重复的登录码,
        String code = UUID.randomUUID().toString();
        // 二维码过期时间（10分钟）
        int expireTime = 10 * 60;
        WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, expireTime);
        return ticket.getUrl();
    }

    @Override
    public WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            log.info("\n接收到请求消息，内容：{}", JSONUtil.toJsonStr(message));
            return messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }
        return null;
    }
}
