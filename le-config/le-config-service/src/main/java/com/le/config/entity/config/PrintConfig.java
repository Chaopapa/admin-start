package com.le.config.entity.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 严秋旺
 * @since 2019-04-23 13:54
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class PrintConfig extends BaseConfig {
    private static final long serialVersionUID = -899795397269814400L;
    /**
     * 飞鹅云后台注册用户名
     */
    @NotNull(message = "用户名不能为空")
    private String user;
    /**
     * 鹅云后台的开发者信息UKEY
     */
    private String key;
    /**
     * 打印机编号
     */
    private String sn;
    /**
     * 模板
     */
    private String tpl;
}
