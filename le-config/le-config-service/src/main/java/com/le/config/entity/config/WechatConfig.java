package com.le.config.entity.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * @author 严秋旺
 * @since 2019-02-28 17:19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class WechatConfig extends BaseConfig {
    private static final long serialVersionUID = 4259953952006513878L;
    //region 小程序
    @NotEmpty(message = "小程序appid为空")
    private String xcxAppId = "";
    private String xcxAppSecret = "";
    //endregion
    //region 公众号
    private String gzhAppId = "";
    private String gzhAppSecret = "";
    private String gzhDeviceToken = "";
    //endregion
    //region 支付信息
    /**
     * 支付回调地址
     */
    private String domain = "";
    //endregion
}
