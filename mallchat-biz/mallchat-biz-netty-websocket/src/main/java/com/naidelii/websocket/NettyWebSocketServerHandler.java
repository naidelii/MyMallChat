package com.naidelii.websocket;

import cn.hutool.json.JSONUtil;
import com.naidelii.websocket.domain.enums.WebSocketRequestTypeEnum;
import com.naidelii.websocket.domain.vo.request.WebSocketRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author naidelii
 */
@Slf4j
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {
        if (event instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            log.info("握手完成");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame message) {
        // 获取到的新消息
        String text = message.text();
        log.info("接收到的新消息：{}", text);
        // 转为实体类
        WebSocketRequest bean = JSONUtil.toBean(text, WebSocketRequest.class);
        // 获取到对应的类型
        WebSocketRequestTypeEnum typeEnum = WebSocketRequestTypeEnum.of(bean.getType());
        switch (typeEnum) {
            case LOGIN:
                System.out.println("请求二维码");
                break;
            case HEARTBEAT:
                break;
            default:
                log.warn("未知类型");
        }
    }
}
