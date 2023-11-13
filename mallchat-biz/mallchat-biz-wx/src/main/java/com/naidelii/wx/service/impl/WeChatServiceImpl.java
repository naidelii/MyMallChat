package com.naidelii.wx.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.naidelii.wx.service.IWeChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeChatServiceImpl implements IWeChatService {

    private static final Set<String> CODE_USER = new HashSet<>();

    private final WxMpService wxMpService;
    private final WxMpMessageRouter messageRouter;


    @Override
    @SneakyThrows
    public String generateQRCode() {
        // 生成随机不重复的登录码,
        String uuid = UUID.randomUUID().toString();
        // 二维码过期时间（10分钟）
        int expireTime = 10 * 60;
        // 记录随机码，用于在扫码的时候知道是该用户
        CODE_USER.add(uuid);
        log.info("生成的code：{}", uuid);
        // 生成带参数的临时二维码
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        WxMpQrCodeTicket ticket = qrcodeService.qrCodeCreateTmpTicket(uuid, expireTime);
        return ticket.getUrl();
    }

    @Override
    public WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            log.info("\n消息内容：{}", JSONUtil.toJsonStr(message));
            return messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }
        return null;
    }
}
