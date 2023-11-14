package com.naidelii.chat.wx.controller;

import cn.hutool.json.JSONUtil;
import com.naidelii.exception.MallChatException;
import com.naidelii.wx.service.adapter.MessageTextBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author naidelii
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat/officialAccount")
public class OfficialAccountController {

    private final WxMpService wxService;
    private final WxMpMessageRouter messageRouter;

    @GetMapping("/callBack")
    public RedirectView callBack(@RequestParam String code) {
        log.info("\n消息内容：{}", code);
        return null;
    }


    /**
     * 接收微信的事件推送：例如关注公众号、取消关注、发消息等
     *
     * @param signature 微信加密签名，signature结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     */
    @PostMapping(produces = "text/xml; charset=UTF-8")
    public String receivePushEvents(@RequestBody String requestBody,
                                    @RequestParam("signature") String signature,
                                    @RequestParam("timestamp") String timestamp,
                                    @RequestParam("nonce") String nonce) {
        // 验签
        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            log.error("验签未通过，可能属于伪造的请求！");
            throw new MallChatException("非法请求！");
        }
        // 获取推送的事件内容
        WxMpXmlMessage message = WxMpXmlMessage.fromXml(requestBody);
        // 通过路由处理不同的消息
        WxMpXmlOutMessage outMessage = route(message);
        // 回复用户
        return outMessage.toXml();
    }


    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            log.info("\n消息内容：{}", JSONUtil.toJsonStr(message));
            return messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }
        String content = "服务器开小差了，请稍后再试！";
        return new MessageTextBuilder().build(content, message);
    }

}
