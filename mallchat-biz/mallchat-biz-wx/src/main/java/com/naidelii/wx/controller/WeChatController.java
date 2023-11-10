package com.naidelii.wx.controller;

import com.naidelii.exception.MallChatException;
import com.naidelii.wx.service.IWeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.web.bind.annotation.*;

/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx")
public class WeChatController {

    private final WxMpService wxService;
    private final IWeChatService weChatService;

    /**
     * 验签
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return String
     */
    @GetMapping
    public String authGet(@RequestParam(name = "signature") String signature,
                          @RequestParam(name = "timestamp") String timestamp,
                          @RequestParam(name = "nonce") String nonce,
                          @RequestParam(name = "echostr") String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        // 校验签名
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }


    /**
     * 接收微信的事件推送：例如关注公众号、取消关注、发消息等
     *
     * @param signature    微信加密签名，signature结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce参数。
     * @param timestamp    时间戳
     * @param nonce        随机数
     * @param openId       微信id
     * @param encType      加密类型（暂未启用加密消息）
     * @param msgSignature 加密的消息
     */
    @PostMapping(produces = "text/xml; charset=UTF-8")
    public String receivePushEvents(@RequestBody String requestBody,
                                    @RequestParam("signature") String signature,
                                    @RequestParam("timestamp") String timestamp,
                                    @RequestParam("nonce") String nonce,
                                    @RequestParam("openid") String openId,
                                    @RequestParam(name = "encrypt_type", required = false) String encType,
                                    @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openId, signature, encType, msgSignature, timestamp, nonce, requestBody);
        // 验签
        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            log.error("验签未通过，可能属于伪造的请求！");
            throw new MallChatException("非法请求！");
        }
        // 获取推送的事件内容
        WxMpXmlMessage message = WxMpXmlMessage.fromXml(requestBody);
        // 通过路由处理不同的消息
        WxMpXmlOutMessage outMessage = weChatService.route(message);
        // 回复用户
        return outMessage.toXml();
    }

    /**
     * 生成微信二维码
     * @return 二维码地址
     */
    @GetMapping("/generateQRCode")
    public String generateQRCode() {
        return weChatService.generateQRCode();
    }
}
