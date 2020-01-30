package com.le.cs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.le.core.base.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author WXY
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cs_mini_program")
public class MiniProgram extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 平台用户ID
     */
    private Long openUserId;

    /**
     * 名称
     */
    private String name;

    /**
     * appid
     */
    private String appid;

    /**
     * secret
     */
    private String secret;

    /**
     * 令牌
     */
    private String token;

    /**
     * 加密密钥
     */
    private String aesKey;

    /**
     * 消息通知地址
     */
    private String msgNotifyUrl;


}
