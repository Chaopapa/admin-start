package com.le.config.entity.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-04-01 17:30
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GlobalConfig extends BaseConfig implements Serializable {
    private static final long serialVersionUID = -6759848406295703307L;

    /**
     * 大标题
     */
    @NotEmpty(message = "大标题不能为空")
    private String title = "龙屹信息管理系统";
    /**
     * 短标题
     */
    @NotEmpty(message = "短标题不能为空")
    private String shortTitle = "LES";
    /**
     * 网站图标
     */
    private String favicon = "";
    /**
     * 登录LOGO
     */
    private String loginLogo = "";
    /**
     * 欢迎语
     */
    private String welcome = "欢迎使用！";
    /**
     * 登录页是否缓存密码
     */
    private Boolean cachePassword = true;
    /**
     * 主题
     */
    private String theme = "skin-blue-light";
}
