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
 * @since 2019-05-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cs_customer")
public class Customer extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 第三方平台ID
     */
    private String platformUserId;

    /**
     * 平台类型
     */
    private String platformType;

    /**
     * 名称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String mobile;


}
