package com.naidelii.chat.ws.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.naidelii.chat.user.domain.entity.SysUser;
import com.naidelii.chat.user.service.ISysUserService;
import com.naidelii.chat.ws.domain.dto.WebSocketChannelExtraDto;
import com.naidelii.chat.ws.domain.vo.response.LoginSuccess;
import com.naidelii.chat.ws.domain.vo.response.ResponseMessage;
import com.naidelii.chat.ws.service.IWebSocketService;
import com.naidelii.chat.ws.service.adapter.MessageAdapter;
import com.naidelii.exception.MallChatException;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author naidelii
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketServiceImpl implements IWebSocketService {

    private final ISysUserService userService;

    /**
     * 存储登陆码和Channel
     */
    private static final Cache<String, Channel> WAIT_LOGIN_MAP = Caffeine
            .newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofHours(1))
            .build();
    /**
     * 在线用户列表
     */
    private static final ConcurrentHashMap<Channel, WebSocketChannelExtraDto> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    /**
     * 所有在线的用户和对应的Channel
     */
    private static final ConcurrentHashMap<String, Channel> ONLINE_UID_MAP = new ConcurrentHashMap<>();


    @Override
    public void online(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WebSocketChannelExtraDto());
    }

    @Override
    public void offline(Channel channel) {
        // 移除channel
        ONLINE_WS_MAP.remove(channel);
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
        // 获取channel
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (channel == null) {
            log.error("code: {}对应的channel不存在", code);
            throw new MallChatException("请重新扫码！");
        }
        // 将openIo和channel进行缓存
        ONLINE_UID_MAP.put(openId, channel);
    }

    @Override
    public void scanLoginSuccess(String openId) {
        // 根据openId取出Channel
        Channel channel = ONLINE_UID_MAP.get(openId);
        // 根据openId获取用户信息
        SysUser sysUser = userService.getByOpenId(openId);
        // 将登陆成功的结果返回给前端
        ResponseMessage<LoginSuccess> message = MessageAdapter.buildLoginSuccessResp(sysUser, null);
        // 发送消息
        sendMessage(channel, message);
    }


}
