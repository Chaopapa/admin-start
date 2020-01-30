package com.le.cs.ws.service;

import com.le.cs.ws.service.rest.WebSocketRest;
import io.netty.channel.Channel;

/**
 * @author 严秋旺
 * @since 2019-05-13 17:12
 **/
public interface WebSocketChannelHandler {
    /**
     * 获取通道管理器
     *
     * @param channel 通道
     * @return 通道管理器
     */
    WebSocketChannel findWebSocketChannel(Channel channel);

    /**
     * 向客户端推送消息
     *
     * @param channel       连接通道
     * @param webSocketRest 推送内容
     */
    void push(Channel channel, WebSocketRest webSocketRest);

    /**
     * 向客户端推送消息
     *
     * @param id            连接通道id
     * @param webSocketRest 推送内容
     */
    void push(String id, WebSocketRest webSocketRest);

    /**
     * 向客户端推送消息
     *
     * @param webSocketChannel
     * @param webSocketRest    推送内容
     */
    void push(WebSocketChannel webSocketChannel, WebSocketRest webSocketRest);
}
