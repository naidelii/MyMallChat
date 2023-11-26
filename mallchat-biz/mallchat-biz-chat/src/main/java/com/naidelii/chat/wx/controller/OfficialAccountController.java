package com.naidelii.chat.wx.controller;

import cn.hutool.json.JSONUtil;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.base.exception.MallChatException;
import com.naidelii.chat.wx.service.IWeChatService;
import com.naidelii.wx.service.adapter.MessageTextBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author naidelii
 */
@Api(tags = "公众号")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat/officialAccount")
public class OfficialAccountController {

    private final WxMpService wxMpService;
    private final IWeChatService weChatService;
    private final WxMpMessageRouter messageRouter;

    /**
     * 验签
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return String
     */
    @ApiOperation("验签")
    @GetMapping
    public String authGet(@RequestParam(name = "signature") String signature,
                          @RequestParam(name = "timestamp") String timestamp,
                          @RequestParam(name = "nonce") String nonce,
                          @RequestParam(name = "echostr") String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
        // 校验签名
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    /**
     * 微信点击身份认证的接口回调
     *
     * @param code 接口调用凭证
     * @return RedirectView
     */
    @GetMapping("/callBack")
    public RedirectView callBack(@RequestParam String code) {
        log.info("\ncallBack code：{}", code);
        try {
            WxOAuth2Service oAuth2Service = wxMpService.getOAuth2Service();
            // 根据code获取token
            WxOAuth2AccessToken accessToken = oAuth2Service.getAccessToken(code);
            // 根据token获取用户信息
            WxOAuth2UserInfo userInfo = oAuth2Service.getUserInfo(accessToken, CommonConstants.CHINESE);
            // 用户授权
            weChatService.authorization(userInfo);
        } catch (Exception e) {
            log.error("callBack error！", e);
        }
        // 重定向指定网站
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://gpt.naidelii.top");
        return redirectView;
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
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
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


    /**
     * 路由对应的消息
     * @param message 收到的消息
     * @return WxMpXmlOutMessage
     */
    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            log.info("\n消息内容：{}", JSONUtil.toJsonStr(message));
            return messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
            String content = "服务器开小差了，请稍后再试！";
            return new MessageTextBuilder().build(content, message);
        }
    }

}
