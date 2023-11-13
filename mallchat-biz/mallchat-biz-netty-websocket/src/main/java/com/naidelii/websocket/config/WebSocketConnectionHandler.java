package com.naidelii.websocket.config;

import com.naidelii.websocket.handler.WebSocketServerListenerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author naidelii
 * 通道初始化
 */
public class WebSocketConnectionHandler extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道以及配置对应管道的处理器
     *
     * @param channel 通道信息
     */
    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        // 30秒客户端没有向服务器发送心跳则关闭连接
        pipeline.addLast(new IdleStateHandler(30, 0, 0));
        // 因为使用http协议，所以需要使用http的编码器，解码器，将socket数据流转换为HttpRequest
        // WebSocket协议握手时需要使用HttpServerCodec，握手结束后只需要使用WebSocket协议（协议升级）
        pipeline.addLast(new HttpServerCodec());
        // http数据在传输过程中是分段的，HttpObjectAggregator可以把多个段聚合起来（
        // 这就是为什么当浏览器发送大量数据时，就会发出多次http请求的原因）
        // HttpServerCodec与HttpObjectAggregator会在第一次http请求后被移除掉（后面都是升级后的WS请求）
        pipeline.addLast(new HttpObjectAggregator(8192));
        // 以块方式写，添加 chunkedWriter 处理器
        pipeline.addLast(new ChunkedWriteHandler());
        // WebSocket握手处理器，处理WebSocket协议的握手（第一次HTTP请求），ping-pong处理，close处理，
        // 对于二进制或者文件数据，直接交付给下层处理
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义handler，处理自定义的业务逻辑
        pipeline.addLast(new WebSocketServerListenerHandler());
    }
}
