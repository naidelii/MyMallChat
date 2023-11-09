package com.naidelii.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author naidelii
 */
@Slf4j
@Configuration
public class NettyWebSocketServer {
    /**
     * ws server 端口
     */
    private static final int WEB_SOCKET_PORT = 8090;
    /**
     * 配置服务端的NIO线程组
     * NioEventLoopGroup 是用来处理I/O操作的Reactor线程组
     * bossGroup：用来接收进来的连接，workerGroup：用来处理已经被接收的连接,进行socketChannel的网络读写，
     * bossGroup接收到连接后就会把连接信息注册到workerGroup
     * workerGroup的EventLoopGroup默认的线程数是CPU核数的二倍
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());


    /**
     * 启动 ws server
     *
     * @throws InterruptedException 异常
     */
    @PostConstruct
    public void start() throws InterruptedException {
        run();
    }

    /**
     * 关闭 ws server
     */
    @PreDestroy
    public void destroy() {
        Future<?> future = bossGroup.shutdownGracefully();
        Future<?> future1 = workerGroup.shutdownGracefully();
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
        log.info("关闭 ws server 成功");
    }

    /**
     * 启动 ws server
     * @throws InterruptedException 异常
     */
    public void run() throws InterruptedException {
        // serverBootstrap 是一个启动NIO服务的辅助启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 配置服务端的NIO线程组
        serverBootstrap
                // 设置group，将bossGroup，workerGroup线程组传递到ServerBootstrap
                .group(bossGroup, workerGroup)
                // ServerSocketChannel是以NIO的selector为基础进行实现的，用来接收新的连接，这里告诉Channel通过NioServerSocketChannel获取新的连接
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 为bossGroup添加日志处理器，用于打印服务器启动日志
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
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
                        pipeline.addLast(new NettyWebSocketServerHandler());
                    }
                });
        // 启动服务器，监听端口，阻塞直到启动成功
        serverBootstrap.bind(WEB_SOCKET_PORT).sync();
    }

}
