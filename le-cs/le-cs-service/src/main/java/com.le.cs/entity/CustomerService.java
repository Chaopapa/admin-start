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
@TableName("cs_customer_service")
public class CustomerService extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 平台用户ID
     */
    private Long openUserId;


}