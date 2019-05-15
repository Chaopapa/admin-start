package com.le.cs.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CustomerLoginVo implements Serializable {
    private static final long serialVersionUID = -5613367739602734879L;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-8]\\d{9}$", message = "手机号码格式错误")
    private String mobile;

    @NotEmpty(message = "短信验证码不能为空")
    private String code;

}
