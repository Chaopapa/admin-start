package com.le.cs.ws.service.component;

import com.le.cs.ws.service.FrameType;
import com.le.cs.ws.service.WebSocketChannel;
import com.le.cs.ws.service.WebSocketFrame;
import com.le.cs.ws.service.annotation.FrameMapping;
import org.springframework.stereotype.Component;

/**
 * @author 严秋旺
 * @since 2019-05-15 17:01
 **/
@Component
@FrameMapping(value = FrameType.AUTH)
public class MessageFrameComponent implements FrameComponent{
    @Override
    public void receiveFrame(WebSocketChannel webSocketChannel, WebSocketFrame webSocketFrame) {

    }
}
