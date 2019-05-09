package com.le.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.le.core.base.SuperEntity;
import com.le.system.entity.enums.ResourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


/**
 * 资源表
 * @author lz
 * @since 2019/5/9 9:19
 */
@Data
@TableName("sys_resource")
@EqualsAndHashCode(callSuper = false)
public class SysResource extends SuperEntity {
	private static final long serialVersionUID = 1L;

    /**
     * 父菜单ID，一级菜单为0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String permission;

    /**
     * 类型  0：菜单   2：权限
     */
    private ResourceType type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer seq;
    /**
     * 深度
     */
    private Integer deep;

    /**
     * 前台路由
     */
    private String path;

    /**
     * 父级 广度
     */
    private String parentPath;

    /**
     * 子级菜单
     */
    @TableField(exist = false)
    private transient List<SysResource> children;

    public void addChild(SysResource child){
        if(children == null){
            children = new ArrayList<>();
        }

        children.add(child);
    }
}
