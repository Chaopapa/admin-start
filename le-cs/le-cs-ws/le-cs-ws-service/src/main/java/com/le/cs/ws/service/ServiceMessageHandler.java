package com.le.cs.ws.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客服消息处理器
 *
 * @author 严秋旺
 * @since 2019-05-10 15:19
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class ServiceMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private Map<String, WSChannelHandler> channelMap = new ConcurrentHashMap<>();

    /**
     * 对channel生成唯一的可持久化标识
     *
     * @param channel
     * @return
     */
    private String id(Channel channel) {
        return channel.id().asLongText();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String id = id(channel);
        WSChannelHandler wsChannelHandler = new WSChannelHandler(id, channel);
        log.debug("add " + id);
        channelMap.put(id, wsChannelHandler);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String id = id(channel);
        log.debug("remove " + id);
        channelMap.remove(id);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("通道异常", cause);
        String channelId = ctx.channel().id().asLongText();

        try {
            ctx.close();
        } finally {
            log.info("exception " + channelId);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        log.info(textWebSocketFrame.text());
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame("{\"bbb\":\"hello\"}"));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
