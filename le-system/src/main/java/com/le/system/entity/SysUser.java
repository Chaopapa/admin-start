package com.le.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.le.core.base.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SysUser
 * @Author lz
 * @Description 系统用户表
 * @Date 2018/11/16 16:36
 * @Version V1.0
 **/
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends SuperEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @NotBlank(message = "名称为空")
    private String name;
    /**
     * 登录名
     */
    @NotBlank(message = "用户名为空")
    private String username;
    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 手机
     */
    private String phone;
    /**
     * 状态 0 可用 1 不可用
     */
    @NotNull(message = "状态为空")
    private Integer status;

}
