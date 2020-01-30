package com.le.cs.ws.service.rest;

import com.le.cs.ws.service.FrameType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 严秋旺
 * @since 2019-05-14 11:34
 **/
@Data
@Accessors(chain = true)
public class WebSocketRest implements Serializable {
    private static final long serialVersionUID = -3523658902522366756L;

    private Integer code;
    private String msg;
    private FrameType frameType;
    private Map<String, Object> data;


    public WebSocketRest(FrameType frameType, WebSocketCode code) {
        this(frameType, code.getValue(), code.getMsg());
    }

    public WebSocketRest(FrameType frameType, int code, String msg) {
        this.frameType = frameType;
        this.code = code;
        this.msg = msg;
    }

    public static WebSocketRest success() {
        return new WebSocketRest(FrameType.MESSAGE, WebSocketCode.SUCCESS);
    }

    public static WebSocketRest fail() {
        return new WebSocketRest(FrameType.MESSAGE, WebSocketCode.FAIL);
    }

    public boolean isSuccess() {
        return code == null || code == WebSocketCode.SUCCESS.getValue();
    }

    public WebSocketRest putData(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }

        data.put(key, value);
        return this;
    }
}
