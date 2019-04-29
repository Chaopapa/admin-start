package com.le.sso.entity.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-04-28 18:48
 **/
@Data
public class SystemUserLogin implements Serializable {
    private static final long serialVersionUID = 8116351856976254795L;
    @NotEmpty(message = "用户名为空")
    private String username;
    @NotEmpty(message = "登录密码为空")
    private String password;
}
