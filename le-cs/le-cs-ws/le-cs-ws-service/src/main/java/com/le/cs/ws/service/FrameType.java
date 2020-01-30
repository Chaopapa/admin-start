package com.le.cs.ws.service;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 严秋旺
 * @since 2019-05-13 15:31
 **/
public enum FrameType {
    /**
     * 认证
     */
    AUTH,
    /**
     * 消息
     */
    MESSAGE;
    public static final Map<String, FrameType> map;

    static {
        FrameType[] values = FrameType.values();
        map = new HashMap<>(values.length);

        for (FrameType value : values) {
            map.put(value.toString(), value);
        }
    }

    @JsonCreator
    public static FrameType parse(String value) {
        return map.get(value);
    }
}
