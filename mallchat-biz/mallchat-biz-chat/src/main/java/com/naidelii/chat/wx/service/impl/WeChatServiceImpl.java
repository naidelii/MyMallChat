package com.naidelii.chat.wx.service.impl;

import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.user.service.adapter.UserAdapter;
import com.naidelii.chat.ws.service.IWebSocketService;
import com.naidelii.chat.wx.config.WeChatOtherProperties;
import com.naidelii.chat.wx.service.IWeChatService;
import com.naidelii.constant.CommonConstants;
import com.naidelii.exception.MallChatException;
import com.naidelii.wx.config.WeChatProperties;
import com.naidelii.wx.service.adapter.MessageTextBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author naidelii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WeChatServiceImpl implements IWeChatService {

    /**
     * 用户的openId和前端登录场景code的映射关系
     */
    private static final String AUTHORIZATION_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    private final WeChatProperties weChatProperties;
    private final ISysUserService userService;
    private final WxMpService wxMpService;
    private final WeChatOtherProperties otherProperties;
    private final IWebSocketService webSocketService;

    @Override
    @SneakyThrows
    public String generateQRCode(String code) {
        // 生成带参数的临时二维码
        WxMpQrcodeService qrcodeService = wxMpService.getQrcodeService();
        WxMpQrCodeTicket ticket = qrcodeService.qrCodeCreateTmpTicket(code, otherProperties.getCodeExpirationTime());
        return ticket.getUrl();
    }

    /**
     * 处理扫码事件
     *
     * @param wxMessage wxMessage
     * @return WxMpXmlOutMessage
     */
    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMessage) {
        // 场景code
        String code = getEventKey(wxMessage);
        if (StringUtils.isEmpty(code)) {
            // 不做任何处理
            log.error("scan error code is empty");
            throw new MallChatException("操作失败！");
        }
        // 微信用户的openId
        String openId = wxMessage.getFromUser();
        // 根据openId查询用户是否存在
        SysUser dbUser = userService.getByOpenId(openId);
        // 如果已经注册，且已经授权过，则直接走登录成功
        boolean registered = Objects.nonNull(dbUser);
        if (registered && CommonConstants.USER_ACTIVATION_STATE.equals(dbUser.getStatus())) {
            // 通过code找到对应的channel，然后给前端推送消息
            webSocketService.scanLoginSuccess(code, dbUser.getId());
            String content = "登录成功！";
            return new MessageTextBuilder().build(content, wxMessage);
        }
        // 如果用户不存在，则注册
        if (!registered) {
            SysUser sysUser = UserAdapter.buildSaveUser(openId);
            userService.register(sysUser);
        }
        // 将openId和channel维护起来
        webSocketService.maintainRelationships(code, openId);
        // 给前端推送一条消息，准备授权
        webSocketService.waitAuthorize(code);
        // 发送授权链接
        return sendAuthorizationLink(wxMessage);
    }

    @Override
    public void authorization(WxOAuth2UserInfo userInfo) {
        // 根据openId查询用户是否存在
        String openId = userInfo.getOpenid();
        SysUser dbUser = userService.getByOpenId(openId);
        if (Objects.isNull(dbUser)) {
            log.error("{}用户不存在！", openId);
            throw new MallChatException("用户不存在！");
        }
        // 如果用户还是冻结状态补充用户信息
        if (CommonConstants.USER_FROZEN_STATE.equals(dbUser.getStatus())) {
            fillUserInfo(dbUser.getId(), userInfo);
        }
        // 通过openId找到code
        String code = webSocketService.getWaitAuthorizeCode(openId);
        // 给用户推送消息，登录成功
        webSocketService.scanLoginSuccess(code, dbUser.getId());
    }

    private void fillUserInfo(String id, WxOAuth2UserInfo userInfo) {
        // 更新用户信息
        SysUser user = UserAdapter.buildAuthorizeUser(id, userInfo);
        try {
            userService.updateById(user);
        } catch (DuplicateKeyException e) {
            log.info("fill userInfo duplicate uid:{},info:{}", id, userInfo);
        } catch (Exception e) {
            log.error("fill userInfo fail uid:{},info:{}", id, userInfo);
        }
    }

    /**
     * 发送授权链接
     *
     * @param wxMessage wxMessage
     * @return WxMpXmlOutMessage
     */
    private WxMpXmlOutMessage sendAuthorizationLink(WxMpXmlMessage wxMessage) {
        // 回调地址
        String callBackUrl;
        try {
            String callBack = otherProperties.getDomain() + otherProperties.getCallback();
            callBackUrl = URLEncoder.encode(callBack, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            log.error("授权链接编码失败", e);
            throw new MallChatException("授权链接编码失败!");
        }
        String skipUrl = String.format(AUTHORIZATION_URL, weChatProperties.getAppId(), callBackUrl);
        // 回复消息
        String content = "请点击链接授权：<a href=\"" + skipUrl + "\">登录</a>";
        return new MessageTextBuilder().build(content, wxMessage);
    }

    /**
     * 处理事件码
     *
     * @param wxMessage wxMessage
     * @return 事件码
     */
    private String getEventKey(WxMpXmlMessage wxMessage) {
        // 处理事件码
        String eventKey = wxMessage.getEventKey();
        try {
            if (eventKey.startsWith(CommonConstants.QR_SCENE_PREFIX)) {
                eventKey = eventKey.replace(CommonConstants.QR_SCENE_PREFIX, "");
            }
            return eventKey;
        } catch (Exception e) {
            log.error("getEventKey error，eventKey{}", eventKey, e);
            return null;
        }
    }

}
