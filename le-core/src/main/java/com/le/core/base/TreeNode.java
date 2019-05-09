package com.le.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

/**
 * @author lz
 * @since 2019/5/6 12:26
 **/
public class TreeNode implements java.io.Serializable{
    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String label;
    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TreeNode> children;
    /**
     * 父节点
     */
    private String pId;

    /**
     * 路径
     */
    private String parentPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public TreeNode(){}

    public TreeNode(String id, String pId, String label, List<TreeNode> children) {
        this.id = id;
        this.pId = pId;
        this.label = label;
        this.children = children;
    }

    public TreeNode(String id, String pId, String label, String parentPath, List<TreeNode> children) {
        this.id = id;
        this.pId = pId;
        this.label = label;
        this.parentPath = parentPath;
        this.children = children;
    }
}
