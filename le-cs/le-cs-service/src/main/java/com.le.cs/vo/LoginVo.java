package com.le.cs.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-01-30 9:34
 **/
@Data
@Accessors(chain = true)
public class LoginVo implements Serializable {
    @NotEmpty(message = "code为空")
    private String code;
}
