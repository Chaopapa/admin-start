package com.le.core.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Set;

/**
 * @author lz
 * @since 2019/5/6 15:24
 **/
public class Tree implements Serializable, Comparable{
    /**
     * value id
     */
    private String value;
    /**
     * 名称
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String label;
    /**
     * 子节点
     */
    private Set<Tree> children;
    /**
     * 父节点
     */
    private String pId;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Tree> getChildren() {
        return children;
    }

    public void setChildren(Set<Tree> children) {
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

    public Tree(){}

    public Tree(String value, String pId, String label, Set<Tree> children) {
        this.value = value;
        this.pId = pId;
        this.label = label;
        this.children = children;
    }

    @Override
    public int compareTo(Object obj) {
        if(!(obj instanceof Tree)) {
            throw new RuntimeException("不是CascaderNode对象");
        }
        Tree n =(Tree)obj;
        return this.value.compareTo(n.getValue());
    }
}
