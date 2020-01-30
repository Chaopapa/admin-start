package com.le.cs.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author 严秋旺
 * @since 2019-05-15 14:30
 **/
public enum LoginType implements IEnum<String> {
    WECHAT_MINI_APP("wechatMiniApp", "微信小程序"),
    APP("app", "app"),
    WEB("web", "web");

    private String value;
    private String desc;

    LoginType(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }
}
