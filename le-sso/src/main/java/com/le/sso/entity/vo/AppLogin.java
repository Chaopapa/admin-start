package com.le.sso.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AppLogin implements Serializable {
    private static final long serialVersionUID = 8116351856976254795L;

    @NotEmpty(message = "登录名为空")
    private String username;
    @NotEmpty(message = "登录密码为空")
    private String password;
}
