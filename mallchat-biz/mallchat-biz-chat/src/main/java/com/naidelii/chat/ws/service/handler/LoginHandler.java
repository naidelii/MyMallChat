package com.naidelii.chat.ws.service.handler;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.naidelii.chat.ws.domain.enums.RequestTypeEnum;
import com.naidelii.chat.ws.domain.vo.response.LoginUrl;
import com.naidelii.chat.ws.domain.vo.response.ResponseMessage;
import com.naidelii.chat.ws.service.RequestMessageStrategyHandler;
import com.naidelii.chat.ws.service.adapter.MessageAdapter;
import com.naidelii.chat.wx.service.IWeChatService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author naidelii
 * 处理身份验证逻辑的 WebSocket 请求处理策略实现类
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginHandler implements RequestMessageStrategyHandler {

    private final IWeChatService weChatService;

    /**
     * 存储二维码code和
     */
    private static final Cache<String, Channel> WAIT_LOGIN_MAP = Caffeine
            .newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofHours(1))
            .build();

    @Override
    public RequestTypeEnum type() {
        return RequestTypeEnum.LOGIN;
    }

    @Override
    public void handleMessage(String data, Channel channel) {
        // 生成随机不重复的登录码，并将channel存在本地cache中
        String code = generateLoginCode(channel);
        // 根据登录码生成二维码
        String url = weChatService.generateQRCode(code);
        // 将二维码返回给前端
        ResponseMessage<LoginUrl> loginUrlResponseMessage = MessageAdapter.buildLoginResp(url);
        // 发送消息
        sendMessage(channel, loginUrlResponseMessage);
    }

    public void sendMessage(Channel channel, ResponseMessage<?> message) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(message)));
    }

    private String generateLoginCode(Channel channel) {
        String code;
        do {
            code = UUID.randomUUID().toString();
        } while (WAIT_LOGIN_MAP.asMap().containsKey(code));
        // 将channel和登陆码进行缓存
        WAIT_LOGIN_MAP.put(code, channel);
        return code;
    }

}
