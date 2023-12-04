package com.naidelii.chat.ws.intercepter;

import com.naidelii.websocket.util.NettyUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

/**
 * @author naidelii
 * 握手拦截器
 */
@Slf4j
public class PortalHandshakeInterceptor extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 如果是http请求则进行处理
        if (msg instanceof HttpRequest) {
            Channel channel = ctx.channel();
            // Request对象
            HttpRequest request = (HttpRequest) msg;
            // 获取请求头
            HttpHeaders headers = request.headers();
            // 获取用户ip
            String ip = headers.get("X-Real-IP");
            // 如果没经过nginx，就直接获取远端地址
            if (StringUtils.isEmpty(ip)) {
                InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
                ip = address.getAddress().getHostAddress();
            }
            // 将ip绑定存储在channel中，在握手成功的时候进行处理
            NettyUtils.setAttr(channel, NettyUtils.IP, ip);
            // 该处理器只需要使用一次（在WS协议升级时使用）
            channel.pipeline().remove(this);
        }
        // 对事件进行传播
        ctx.fireChannelRead(msg);
    }
}
