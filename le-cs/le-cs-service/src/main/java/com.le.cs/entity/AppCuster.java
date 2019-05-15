package com.le.cs.entity;

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
public class AppCuster extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 平台用户ID
     */
    private Long openUserId;

    /**
     * 第三方平台唯一id
     */
    private String platformUserId;

    /**
     * 相对应用id
     */
    private String openId;


}
