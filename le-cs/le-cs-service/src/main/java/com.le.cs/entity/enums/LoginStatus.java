package com.le.cs.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author 严秋旺
 * @since 2019-05-15 14:28
 **/
public enum LoginStatus implements IEnum<String> {
    ONLINE("online", "在线"),
    OFFLINE("offline", "离线");

    private String value;
    private String desc;

    LoginStatus(final String value, final String desc) {
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
