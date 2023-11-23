package com.naidelii.chat.ws.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ILoginService;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.ws.domain.dto.WebSocketChannelExtraDto;
import com.naidelii.chat.ws.domain.vo.response.LoginSuccess;
import com.naidelii.chat.ws.domain.vo.response.ResponseMessage;
import com.naidelii.chat.ws.service.IWebSocketService;
import com.naidelii.chat.ws.service.adapter.MessageAdapter;
import com.naidelii.base.exception.MallChatException;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketServiceImpl implements IWebSocketService {

    private final ISysUserService userService;
    private final ILoginService loginService;

    /**
     * 临时存储登陆码和Channel
     */
    private static final Cache<String, Channel> WAIT_LOGIN_MAP = Caffeine
            .newBuilder()
            .maximumSize(1000)
            // 过期时间
            .expireAfterWrite(Duration.ofHours(1))
            .build();

    /**
     * 在线用户列表
     */
    private static final ConcurrentHashMap<Channel, WebSocketChannelExtraDto> ONLINE_USER_MAP = new ConcurrentHashMap<>();

    /**
     * 临时存储等待授权的列表（用户openId和对应的Channel）
     */
    private static final Cache<String, String> WAIT_AUTHORIZE_MAP = Caffeine
            .newBuilder()
            .maximumSize(1000)
            // 过期时间
            .expireAfterWrite(Duration.ofHours(1))
            .build();


    @Override
    public void online(Channel channel) {
        ONLINE_USER_MAP.put(channel, new WebSocketChannelExtraDto());
    }

    @Override
    public void offline(Channel channel) {
        // 移除channel
        ONLINE_USER_MAP.remove(channel);
    }

    @Override
    public void sendMessage(Channel channel, ResponseMessage<?> message) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(message)));
    }

    @Override
    public String generateLoginCode(Channel channel) {
        String code;
        do {
            // 生成唯一的code
            code = UUID.randomUUID().toString();
        } while (WAIT_LOGIN_MAP.asMap().containsKey(code));
        // 将channel和登陆码进行缓存
        WAIT_LOGIN_MAP.put(code, channel);
        return code;
    }

    @Override
    public void maintainRelationships(String code, String openId) {
        // 获取channel（并从内存中删除）
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (channel == null) {
            log.error("code: {}对应的channel不存在", code);
            throw new MallChatException("用户已离线！");
        }
        // 将openId和channel进行缓存
        WAIT_AUTHORIZE_MAP.put(openId, code);
    }

    @Override
    public void scanLoginSuccess(String code, String userId) {
        // 确保连接还存在
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) {
            log.error("=========scanLoginSuccess，{}对应channel不存在", userId);
            return;
        }
        // 移除登陆码
        WAIT_LOGIN_MAP.invalidate(code);
        // 获取最新的用户信息
        SysUser user = userService.getById(userId);
        // 调用登录模块，获取token
        String token = loginService.scanQRCodeLogin(userId);
        // 将登陆成功的结果返回给前端
        ResponseMessage<LoginSuccess> message = MessageAdapter.buildLoginSuccessResp(user, token);
        // 发送消息
        sendMessage(channel, message);
    }

    @Override
    public String getWaitAuthorizeCode(String openId) {
        return WAIT_AUTHORIZE_MAP.asMap().remove(openId);
    }

    @Override
    public void waitAuthorize(String code) {
        // 确保连接还存在
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) {
            log.error("=========waitAuthorize，{}对应channel不存在", code);
            return;
        }
        // 通知前端，现在正在等待用户执行授权
        ResponseMessage<?> message = MessageAdapter.buildWaitAuthorize();
        // 发送消息
        sendMessage(channel, message);
    }

}
