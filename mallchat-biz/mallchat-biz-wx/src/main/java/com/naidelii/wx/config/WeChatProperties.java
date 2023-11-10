package com.naidelii.wx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author naidelii
 */
@Data
@ConfigurationProperties(prefix = "wx.mp.config")
public class WeChatProperties {

    /**
     * 设置微信公众号的appId
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;
}
