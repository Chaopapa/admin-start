package com.le.core.rest;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>全局响应代码和消息，原则上2000以上代码作为业务代码使用</p>
 *
 * @author 严秋旺
 * @since 2018/11/27 9:27
 **/
public enum RCode implements IEnum<String> {

    success("0000", "操作成功"),
    fail("0001", "操作失败"),
    formError("0200", "参数错误"),

    tokenNone("1000", "未登录"),
    tokenExpired("1001", "token无效"),
    accessDenny("1003", "没有权限访问"),

    noRegister("1101", "用户未注册"),
    hasRegister("1102", "用户已注册"),
    accountOrPasswordError("1103", "用户名密码错误"),
    smsCodeError("1104", "短信验证码错误");

    private String value;
    private String msg;

    RCode(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
