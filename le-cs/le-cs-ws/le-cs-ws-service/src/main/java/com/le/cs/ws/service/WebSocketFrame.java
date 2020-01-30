package com.le.cs.ws.service;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-05-13 15:30
 **/
@Data
public class WebSocketFrame implements Serializable {

    private static final long serialVersionUID = -8824891711921970691L;

    private FrameType frameType;

    private String content;
}
