package com.le.cs.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class LoginServiceVo implements Serializable {

    @NotEmpty(message = "登录名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
}
