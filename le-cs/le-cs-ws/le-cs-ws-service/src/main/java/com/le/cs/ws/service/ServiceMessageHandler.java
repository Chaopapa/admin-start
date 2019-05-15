package com.le.cs.ws.service;

import com.le.core.util.JsonUtils;
import com.le.cs.ws.service.annotation.FrameMapping;
import com.le.cs.ws.service.component.FrameComponent;
import com.le.cs.ws.service.message.AuthMessage;
import com.le.cs.ws.service.rest.WebSocketCode;
import com.le.cs.ws.service.rest.WebSocketRest;
import com.le.system.entity.SysToken;
import com.le.system.service.ISysTokenService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客服消息处理器
 *
 * @author 严秋旺
 * @since 2019-05-10 15:19
 **/
@Slf4j
@ChannelHandler.Sharable
@Component
public class ServiceMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>
        implements WebSocketChannelHandler, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;
    private Map<String, WebSocketChannel> channelMap = new ConcurrentHashMap<>();
    private Map<FrameType, FrameHandler> frameHandlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, FrameComponent> map = this.applicationContext.getBeansOfType(FrameComponent.class);
        map.forEach((beanName, frameComponent) -> {
            FrameMapping frameMapping = this.applicationContext.findAnnotationOnBean(beanName, FrameMapping.class);
            FrameHandler frameHandler = new FrameHandler(frameComponent, frameMapping);
            frameHandlerMap.put(frameMapping.value(), frameHandler);
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        String id = id(channel);
        WebSocketChannel webSocketChannel = new WebSocketChannel(id, channel);
        log.debug("add " + id);
        channelMap.put(id, webSocketChannel);
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
        String text = textWebSocketFrame.text();
        Channel channel = channelHandlerContext.channel();
        WebSocketChannel webSocketChannel = findWebSocketChannel(channel);
        log.debug("received :" + text);

        try {
            WebSocketFrame frame = JsonUtils.toObject(text, WebSocketFrame.class);
            FrameHandler frameHandler = frameHandlerMap.get(frame.getFrameType());

            if (frameHandler == null) {
                log.warn("未知frame类型[{}],{}", frame.getFrameType(), text);
                WebSocketRest webSocketRest = new WebSocketRest(FrameType.MESSAGE, WebSocketCode.UNKNOWN_FRAME_TYPE);
                push(channel, webSocketRest);
            } else if (frameHandler.isRequireToken() && !webSocketChannel.isAuthenticated()) {
                log.error("非法接入,", channel.remoteAddress());
                channel.close();
                WebSocketRest webSocketRest = new WebSocketRest(FrameType.MESSAGE, WebSocketCode.UNAUTHORIZED);
                push(channel, webSocketRest);
            } else {
                frameHandler.execute(webSocketChannel, frame);
            }
        } catch (JsonUtils.JsonException e) {
            log.warn("数据格式错误，{}\n{}", e.getMessage(), text);
            WebSocketRest webSocketRest = new WebSocketRest(FrameType.MESSAGE, WebSocketCode.DATA_FORMAT_ERROR);
            push(channel, webSocketRest);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

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
    public WebSocketChannel findWebSocketChannel(Channel channel) {
        String id = id(channel);
        return channelMap.get(id);
    }

    @Override
    public void push(Channel channel, WebSocketRest webSocketRest) {
        String json = JsonUtils.toJson(webSocketRest);
        channel.writeAndFlush(new TextWebSocketFrame(json));
    }

    @Override
    public void push(String id, WebSocketRest webSocketRest) {
        WebSocketChannel webSocketChannel = this.channelMap.get(id);

        if (webSocketChannel != null) {
            push(webSocketChannel.getChannel(), webSocketRest);
        }
    }

    @Override
    public void push(WebSocketChannel webSocketChannel, WebSocketRest webSocketRest) {
        push(webSocketChannel.getChannel(), webSocketRest);
    }
}
