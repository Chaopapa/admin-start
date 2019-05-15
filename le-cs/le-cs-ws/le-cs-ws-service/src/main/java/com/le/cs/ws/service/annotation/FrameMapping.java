package com.le.cs.ws.service.annotation;

import com.le.cs.ws.service.FrameType;

/**
 * @author 严秋旺
 * @since 2019-05-15 10:47
 **/
public @interface FrameMapping {
    /**
     * frameType值
     *
     * @return frameType值
     */
    FrameType value();

    /**
     * 是否需要认证，默认true
     *
     * @return true or false
     */
    boolean requireToken() default true;
}
