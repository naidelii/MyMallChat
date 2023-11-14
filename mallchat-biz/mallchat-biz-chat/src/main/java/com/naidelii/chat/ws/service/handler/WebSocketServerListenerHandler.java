package com.naidelii.chat.ws.service.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.naidelii.chat.ws.domain.vo.request.RequestMessage;
import com.naidelii.chat.ws.service.RequestMessageStrategyHandler;
import com.naidelii.chat.ws.service.adapter.MessageAdapter;
import com.naidelii.exception.MallChatException;
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
    private RequestMessageHandler requestMessageHandler;
    private EventHandler eventHandler;

    /**
     * 客户端上线的时候调用
     *
     * @param context 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception {
        log.info("{}客户端上线", context.channel().remoteAddress());
        // 通过Spring上下文容器获取EventHandler
        eventHandler = SpringUtil.getBean(EventHandler.class);
        // 通过Spring上下文容器获取EventHandler
        requestMessageHandler = SpringUtil.getBean(RequestMessageHandler.class);
        // 调用上线方法（保存这个通道）
        eventHandler.online(context.channel());
    }

    /**
     * 客户端掉线的时候调用
     *
     * @param context 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception {
        log.info("{}客户端下线", context.channel().remoteAddress());
        // 调用下线方法（删除这个通道）
        eventHandler.offline(context.channel());
    }

    /**
     * 服务端当read超时, 会调用这个方法
     *
     * @param context 上下文
     * @param event   异常
     */
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
        RequestMessage request = MessageAdapter.buildRequestMessage(text);
        // 根据消息类型获取对应的处理器
        RequestMessageStrategyHandler handler = requestMessageHandler.getHandlerByType(request.getType());
        // 使用处理器处理消息
        handler.handleMessage(request.getData(), context.channel());
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
        // 打印异常信息
        log.error("{}连接出异常：{}", context.channel().remoteAddress(), cause.getMessage());
        // 如果是项目内异常则不关闭连接
        if (!(cause instanceof MallChatException)) {
            // 关闭连接
            context.close();
        }
    }
}
