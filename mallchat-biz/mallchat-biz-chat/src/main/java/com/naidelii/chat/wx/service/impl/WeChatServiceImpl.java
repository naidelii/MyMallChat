package com.naidelii.chat.wx.service.impl;

import com.naidelii.chat.wx.config.WeChatOtherProperties;
import com.naidelii.chat.wx.service.IWeChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
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
    private final WeChatOtherProperties otherProperties;


    @Override
    @SneakyThrows
    public String generateQRCode(String code) {
        // 生成带参数的临时二维码
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        WxMpQrCodeTicket ticket = qrcodeService.qrCodeCreateTmpTicket(code, otherProperties.getCodeExpirationTime());
        return ticket.getUrl();
    }

}
