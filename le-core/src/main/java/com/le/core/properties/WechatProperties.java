package com.le.core.properties;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信相关配置
 *
 * @author 严秋旺
 * @since 2019-04-16 15:04
 **/
@Data
@Accessors(chain = true)
public class WechatProperties implements Serializable {

    private static final long serialVersionUID = 2952962363557919269L;

    /**
     * 退款证书文件路径
     */
    private String certPath;
    /**
     * 微信支付分配的商户号
     */
    private String mchId = "";
    /**
     * 微信支付分配的支付key
     */
    private String mchKey = "";
}
