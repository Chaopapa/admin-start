package com.le.cs.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-02-28 17:11
 **/
@Data
@Accessors(chain = true)
public class AuthVo implements Serializable {
    private static final long serialVersionUID = 3994037591974284850L;

    @NotEmpty(message = "code为空")
    private String code;

    @NotEmpty(message = "encryptedData为空")
    private String encryptedData;

    @NotEmpty(message = "iv为空")
    private String iv;

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
