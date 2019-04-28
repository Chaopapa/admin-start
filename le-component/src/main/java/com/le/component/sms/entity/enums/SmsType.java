package com.le.component.sms.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author 严秋旺
 * @Date 2018-11-19 14:05
 * @Version V1.0
 **/
public enum SmsType implements IEnum<String> {
    REGISTER("register", "注册"), LOGIN("login", "登录"), UPDATE_PASSWORD("updatePassWord", "修改密码");

    private String value;
    private String desc;

    SmsType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }

    @JsonCreator
    public static SmsType getItem(String value) {
        for (SmsType item : values()) {
            if (item.getDesc().equals(value)) {
                return item;
            }
        }

        return null;
    }
}
