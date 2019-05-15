package com.le.cs.ws.service;

import com.le.system.entity.SysToken;
import io.netty.channel.Channel;
import lombok.Data;

import java.io.Serializable;


/**
 * @author 严秋旺
 * @since 2019-05-11 0:39
 **/
@Data
public class WebSocketChannel implements Serializable {
    private static final long serialVersionUID = 7661066791641903734L;

    private final String id;
    private final Channel channel;
    private SysToken token;

    public WebSocketChannel(String id, Channel channel) {
        this.id = id;
        this.channel = channel;
    }

    /**
     * 是否认证
     */
    public boolean isAuthenticated() {
        return token != null;
    }
}
