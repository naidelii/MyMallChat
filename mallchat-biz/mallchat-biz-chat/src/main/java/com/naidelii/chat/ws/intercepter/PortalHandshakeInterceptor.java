package com.naidelii.chat.ws.intercepter;

import com.naidelii.base.constant.CommonConstants;
import com.naidelii.websocket.util.NettyUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author naidelii
 * 握手拦截器
 */
@Slf4j
public class PortalHandshakeInterceptor extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            // 获取请求头
            HttpHeaders headers = request.headers();
            Channel channel = ctx.channel();
            // 获取token
            String token = headers.get(CommonConstants.AUTHORIZATION);
            if (StringUtils.isNotEmpty(token)) {
                // 有token，将token绑定存储在channel中，在握手成功的时候进行处理
                NettyUtils.setAttr(channel, NettyUtils.TOKEN, token);
            }
        }
        // 对事件进行传播
        ctx.fireChannelRead(msg);
    }
}
