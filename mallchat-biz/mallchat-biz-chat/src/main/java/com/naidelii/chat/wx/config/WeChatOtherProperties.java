package com.naidelii.chat.wx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author naidelii
 */
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WeChatOtherProperties {

    /**
     * 回调地址
     */
    private String callback;

    /**
     * 域名
     */
    private String domain;

    /**
     * 二维码过期时间（单位s）默认1分钟
     */
    private Integer codeExpirationTime = 600;

}
