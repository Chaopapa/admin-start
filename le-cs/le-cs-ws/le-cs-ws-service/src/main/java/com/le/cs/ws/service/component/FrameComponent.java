package com.le.cs.ws.service.component;

import com.le.cs.ws.service.WebSocketChannel;
import com.le.cs.ws.service.WebSocketFrame;

/**
 * @author 严秋旺
 * @since 2019-05-15 10:29
 **/
public interface FrameComponent {
    void receiveFrame(WebSocketChannel webSocketChannel, WebSocketFrame webSocketFrame);
}
