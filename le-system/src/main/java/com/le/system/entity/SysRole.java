package com.le.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.le.core.base.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName SysRole
 * @Author lz
 * @Description 角色表
 * @Date 2018/9/30 9:00
 * @Version V1.0
 **/
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends SuperEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 角色名
     */
    @NotEmpty(message = "角色名不为能空")
    private String name;

    /**
     * 角色标识
     */
    @NotEmpty(message = "角色标识不为能空")
    private String role;

}
