package com.le.system.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.le.system.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 严秋旺
 * @since 2019-03-29 20:57
 **/
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUserVo extends SysUser implements Serializable {
    private static final long serialVersionUID = -4744093775817474855L;

    private String roleName;
}
