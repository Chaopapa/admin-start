package com.le.cs.ws.service;

import com.le.cs.ws.service.annotation.FrameMapping;
import com.le.cs.ws.service.component.FrameComponent;
import lombok.Data;

/**
 * @author 严秋旺
 * @since 2019-05-15 10:51
 **/
@Data
public class FrameHandler {
    private final FrameComponent frameComponent;
    private final FrameMapping frameMapping;
    private final FrameType mapping;
    private final boolean requireToken;

    FrameHandler(FrameComponent frameComponent, FrameMapping frameMapping) {
        this.frameComponent = frameComponent;
        this.frameMapping = frameMapping;
        this.mapping = frameMapping.value();
        this.requireToken = frameMapping.requireToken();
    }

    public void execute(WebSocketChannel webSocketChannel, WebSocketFrame webSocketFrame) {
        frameComponent.receiveFrame(webSocketChannel, webSocketFrame);
    }

}
