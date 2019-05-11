package com.le.cs.ws.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author 严秋旺
 * @since 2019-05-10 16:53
 **/
@Slf4j
public class SessionHandler extends ChannelHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
//        channelMap.put(channel.id().toString(), channel);
//        callMap.put(channel.id(), new ConcurrentLinkedQueue<>());
        log.info("connect " + channel);
        channel.write("hello");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info("remove " + channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("通道异常", cause);
        String channelId = ctx.channel().id().toString();

        try {
//            ctx.close();
        } finally {
            log.info("exception " + ctx.channel());
        }
    }
}
