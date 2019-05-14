package com.le.cs.ws.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author 严秋旺
 * @since 2019-05-10 14:27
 **/
@Slf4j
@Component
public class ServiceServerStart {

    /**
     * 创建bootstrap
     */
    private ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS
     */
    private EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * Worker
     */
    private EventLoopGroup work = new NioEventLoopGroup();
    /**
     * 通道适配器
     */
    @Resource
    private ServiceMessageHandler serviceMessageHandler;

    /**
     * NETT服务器配置类
     */
//    @Resource
//    private NettyServerConfig nettyConfig;

    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        log.info("客服websoket服务关闭....");
        //优雅退出
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    /**
     * 开启及服务线程
     */
    @PostConstruct
    public void start() {
        // 从配置文件中(application.yml)获取服务端监听端口号
        int port = 9000;//todo nettyConfig.getPort();
        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO));
        try {
            //设置事件处理
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();


                    // websocket 基于http协议，所以要有http编解码器
                    pipeline.addLast(new HttpServerCodec());
                    // 对写大数据流的支持
                    pipeline.addLast(new ChunkedWriteHandler());
                    // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
                    // 几乎在netty中的编程，都会使用到此hanler
                    pipeline.addLast(new HttpObjectAggregator(1024 * 64));

                    // ====================== 以上是用于支持http协议    ======================

                    // ====================== 增加心跳支持 start    ======================
                    // 针对客户端，如果在1分钟时没有向服务端发送读写心跳(ALL)，则主动断开
                    // 如果是读空闲或者写空闲，不处理
                    pipeline.addLast(new IdleStateHandler(8, 10, 12));
                    // 自定义的空闲状态检测
//                    pipeline.addLast(new HeartBeatAdapter());
                    // ====================== 增加心跳支持 end    ======================

                    // ====================== 以下是支持httpWebsocket ======================

                    /**
                     * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
                     * 本handler会帮你处理一些繁重的复杂的事
                     * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
                     * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
                     */
                    pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

                    // 自定义的handler
                    pipeline.addLast(serviceMessageHandler);
                }
            });
            log.info("netty服务器在[{}]端口启动监听", port);
            serverBootstrap.bind(port).sync();
//            ChannelFuture f = serverBootstrap.bind(port).sync();
//            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.info("[出现异常] 释放资源");
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
