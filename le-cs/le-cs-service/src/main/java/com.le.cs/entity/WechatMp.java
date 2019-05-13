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
 * @since 2019-05-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cs_wechat_mp")
public class WechatMp extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 平台用户ID
     */
    private Long openUserId;

    /**
     * appid
     */
    private String appid;

    /**
     * 名称
     */
    private String name;

    /**
     * secret
     */
    private String secret;

    /**
     * 消息通知地址
     */
    private String msgNotifyUrl;


}
