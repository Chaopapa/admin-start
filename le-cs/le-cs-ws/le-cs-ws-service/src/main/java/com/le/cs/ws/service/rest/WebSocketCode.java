package com.le.cs.ws.service.rest;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author 严秋旺
 * @since 2019-05-14 15:12
 **/
public enum WebSocketCode implements IEnum<Integer> {
    //0-999为系统错误代码
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    UNAUTHORIZED(2, "未认证"),
    TOKEN_ERROR(3, "token无效"),
    DATA_FORMAT_ERROR(4, "格式错误"),
    UNKNOWN_FRAME_TYPE(4, "未知frame类型")

    //1000以上为业务代码
    ;
    private Integer value;
    private String msg;

    WebSocketCode(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

}
