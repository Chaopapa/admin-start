package com.le.cs.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
public class PassWordVo {

    private static final long serialVersionUID = 1L;

    /**
     * 新密码
     */
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

}
