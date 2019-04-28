package com.le.core.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 项目重要配置，通常设置敏感内容，或者不可轻易变更的信息
 **/
@Component
@ConfigurationProperties(prefix = "longe")
@Data
@Accessors(chain = true)
public class LongeProperties implements Serializable {

    private static final long serialVersionUID = 5783136695783512055L;

    /**
     * 是否开启调试模式
     */
    private Boolean debug = false;
    /**
     * 微信配置
     */
    private WechatProperties wechat = new WechatProperties();


}
