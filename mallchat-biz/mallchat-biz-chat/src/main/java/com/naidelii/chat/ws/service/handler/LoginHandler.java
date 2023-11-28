package com.naidelii.chat.ws.service.handler;

import com.naidelii.chat.ws.domain.enums.RequestTypeEnum;
import com.naidelii.chat.ws.domain.vo.response.LoginUrl;
import com.naidelii.chat.ws.domain.vo.response.ResponseMessage;
import com.naidelii.chat.ws.service.IWebSocketService;
import com.naidelii.chat.ws.service.RequestMessageStrategyHandler;
import com.naidelii.chat.ws.service.adapter.MessageAdapter;
import com.naidelii.chat.wx.service.IWeChatService;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author naidelii
 * 登录处理器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginHandler implements RequestMessageStrategyHandler {

    private final IWeChatService weChatService;
    private final IWebSocketService webSocketService;

    @Override
    public RequestTypeEnum type() {
        return RequestTypeEnum.LOGIN;
    }

    @Override
    public void handleMessage(String data, Channel channel) {
        // 生成随机不重复的登录码，并将channel存在本地cache中
        String code = webSocketService.generateLoginCode(channel);
        // 根据登录码生成二维码
        String url = weChatService.generateQRCode(code);
        // 将二维码返回给前端
        ResponseMessage<LoginUrl> loginUrlResponseMessage = MessageAdapter.buildLoginResp(url);
        // 发送消息
        webSocketService.sendMessage(channel, loginUrlResponseMessage);
    }

}
