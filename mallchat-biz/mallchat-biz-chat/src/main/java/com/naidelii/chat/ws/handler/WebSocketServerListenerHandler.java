package com.naidelii.chat.ws.handler;

import cn.hutool.json.JSONUtil;
import com.naidelii.chat.ws.domain.enums.WebSocketRequestTypeEnum;
import com.naidelii.chat.ws.domain.vo.request.WebSocketRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author naidelii
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketServerListenerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 客户端上线的时候调用
     *
     * @param context 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        log.info("{}客户端连接进来了", context.channel().remoteAddress());
        context.fireChannelActive();
    }

    /**
     * 客户端掉线的时候调用
     *
     * @param context 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        log.info("{}连接断开了", context.channel().remoteAddress());
        context.fireChannelInactive();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object event) {
        if (event instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            log.info("握手完成");
        } else if (event instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) event;
            // 如果是读空闲（30秒都都没有收到心跳包）
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                // 关闭用户的连接
                context.channel().close();
            }
        }
    }

    /**
     * 读取客户端信息
     *
     * @param context 上下文
     * @param message 消息
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame message) throws Exception {
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

    /**
     * 异常发生时候调用
     *
     * @param context 上下文
     * @param cause   原因
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
        log.error("{}连接出异常了", context.channel().remoteAddress());
        context.close();
    }
}
