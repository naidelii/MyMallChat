package com.naidelii.web;

import cn.hutool.core.lang.UUID;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WxMpTest {

    @Autowired
    private WxMpService wxMpService;

    @Test
    public void getAQRCode() throws WxErrorException {
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        // 场景值
        String code = UUID.randomUUID().toString();
        // 过期时间（单位s）
        int expireTime = 10 * 60;
        WxMpQrCodeTicket wxMpQrCodeTicket = qrcodeService.qrCodeCreateTmpTicket(code, expireTime);
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }
}
