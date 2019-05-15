package com.le.cs.ws.service.component;

import com.le.core.util.JsonUtils;
import com.le.cs.ws.service.FrameType;
import com.le.cs.ws.service.WebSocketChannel;
import com.le.cs.ws.service.WebSocketChannelHandler;
import com.le.cs.ws.service.WebSocketFrame;
import com.le.cs.ws.service.annotation.FrameMapping;
import com.le.cs.ws.service.message.AuthMessage;
import com.le.cs.ws.service.rest.WebSocketCode;
import com.le.cs.ws.service.rest.WebSocketRest;
import com.le.system.entity.SysToken;
import com.le.system.service.ISysTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author 严秋旺
 * @since 2019-05-15 10:31
 **/
@Slf4j
@Component
@FrameMapping(value = FrameType.AUTH, requireToken = false)
public class AuthFrameComponent implements FrameComponent {
    @Autowired
    @Lazy
    private WebSocketChannelHandler webSocketChannelHandler;

    @Autowired
    private ISysTokenService tokenService;
//    @Autowired
//    private I

    @Override
    public void receiveFrame(WebSocketChannel webSocketChannel, WebSocketFrame frame) {
        String content = frame.getContent();
        AuthMessage authMessage = JsonUtils.toObject(content, AuthMessage.class);
        SysToken token = tokenService.findToken(authMessage.getToken());

        if (token != null) {
            webSocketChannel.setToken(token);
            //todo up line
            WebSocketRest webSocketRest = new WebSocketRest(FrameType.AUTH, WebSocketCode.SUCCESS);
            webSocketChannelHandler.push(webSocketChannel, webSocketRest);
        } else {
            log.warn("token无效，{}", authMessage.getToken());
            WebSocketRest webSocketRest = new WebSocketRest(FrameType.AUTH, WebSocketCode.TOKEN_ERROR);
            webSocketChannelHandler.push(webSocketChannel, webSocketRest);
        }
    }
}
