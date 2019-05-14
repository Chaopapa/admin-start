package com.le.cs.ws.service;

import io.netty.channel.Channel;
import lombok.Data;


/**
 * @author 严秋旺
 * @since 2019-05-11 0:39
 **/
@Data
public class WSChannelHandler {
    private final String id;
    private final Channel channel;
    private String status;

    public WSChannelHandler(String id, Channel channel) {
        this.id = id;
        this.channel = channel;
    }

}
