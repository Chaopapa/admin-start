package com.le.core.rest;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>全局响应代码和消息，原则上10000以上代码作为业务代码使用</p>
 *
 * @author 严秋旺
 * @since 2018/11/27 9:27
 **/
public enum RCode implements IEnum<String> {

    success("0000", "操作成功"),
    fail("1111", "操作失败"),
    sessionOutTime("1112", "会话超时"),
    formError("0200", "数据错误"),
    tokenExpired("10000", "token失效，请重新登录"),
    tokenNone("10001", "缺少token"),
    tokenEmpty("10002", "token为空"),
    accountOrPasswordError("10003", "用户名密码错误"),
    smsCodeError("10004", "短信验证码错误"),
    noRegister("1404", "用户未注册"),
    hasRegister("1101", "用户已注册");

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
