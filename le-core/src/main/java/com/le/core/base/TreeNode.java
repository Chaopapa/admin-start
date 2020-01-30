package com.le.core.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lz
 * @since 2019/5/6 12:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String label;
    /**
     * 子节点
     */
    private List<TreeNode> children;

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
    private String type;

    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 父节点
     */
    private String pId;
    /**
     * 路径
     */
    private String parentPath;

}
