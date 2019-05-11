package com.le.cs.api.service.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-01-30 10:28
 **/
@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {

    private static final long serialVersionUID = -253020801869947740L;

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号为空")
    private String mobile;
    /**
     * 短信验证码
     */
    @NotEmpty(message = "短信验证码为空")
    private String smsCode;

    /**
     * openid
     */
    @NotEmpty(message = "code为空")
    private String code;

    /**
     * 名称
     */
    @NotEmpty(message = "名称为空")
    private String nickName;

    /**
     * 头像
     */
    @NotEmpty(message = "头像为空")
    private String avatar;

    /**
     * 推荐人ID
     */
    private Long referee;


}
