package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.base.Tree;
import com.le.core.base.TreeNode;
import com.le.core.util.Constant;
import com.le.system.entity.SysResource;
import com.le.system.entity.SysRoleResource;
import com.le.system.entity.SysUser;
import com.le.system.entity.enums.ResourceType;
import com.le.system.mapper.SysResourceMapper;
import com.le.system.mapper.SysRoleResourceMapper;
import com.le.system.service.ISysResourceService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源接口实现层
 * @author lz
 * @since 2019/5/9 9:19
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    /**
     * 添加或修改资源
     * @author lz
     * @since 2019/5/9 9:19
     */
    @Override
    public void editData(SysResource sysResource) {
        if (sysResource.getParentId() == null) {
            sysResource.setParentId(ROOT_ID);
        }

        if (ROOT_ID.equals(sysResource.getParentId())) {
            sysResource.setDeep(1);
        } else {
            SysResource parent = baseMapper.selectById(sysResource.getParentId());
            sysResource.setDeep(parent.getDeep() + 1);
        }

        saveOrUpdate(sysResource);
    }

    /**
     * 获取资源管理数据树
     * @author lz
     * @since 2019/5/9 9:19
     */
    @Override
    public List<TreeNode> tree() {
        QueryWrapper<SysResource> qw = new QueryWrapper<>();
        qw.orderByAsc("seq");
        List<SysResource> sysResources = this.list(qw);
        Map<Long, List<SysResource>> map = sysResources.stream().collect(Collectors.groupingBy(SysResource::getParentId));
        return createTree(0L, map);
    }

    private List<TreeNode> createTree(Long pid, Map<Long, List<SysResource>> map) {
        List<TreeNode> trees = Optional.ofNullable(map.get(pid)).orElseGet(() -> new ArrayList<>()).stream().filter(x -> x.getParentId().equals(pid)).map(x -> {
            TreeNode treeNode = new TreeNode(String.valueOf(x.getId()), String.valueOf(x.getParentId()), x.getName(), x.getParentPath(), createTree(x.getId(), map));
            return treeNode;
        }).collect(Collectors.toList());
        return trees;
    }

    /**
     * 角色id查询角色的所有权限
     * @author lz
     * @since 2019/5/9 9:19
     */
    @Override
    public List<SysResource> queryByRoleId(Long roleId) {
        return baseMapper.queryByRoleId(roleId);
    }

    /**
     * 校验资源权限是否存在
     * @author lz
     * @since 2019/5/9 9:19
     */
    @Override
    public boolean exists(Long id, String permission) {
        LambdaQueryWrapper<SysResource> wrapper = new LambdaQueryWrapper<>();

        if (id != null) {
            wrapper.ne(SysResource::getId, id);
        }
        wrapper.eq(SysResource::getPermission, permission);

        Integer count = baseMapper.selectCount(wrapper);
        return count != null && count > 0;

    }

    @Override
    @Transactional
    public void del(Long id) {
        if (id != null) {
            removeById(id);
            delRoleResourceByResourceId(id);
        }
    }

    private void delRoleResourceByResourceId(Long resourceId) {
        QueryWrapper<SysRoleResource> qw = new QueryWrapper<>();
        qw.eq("resource_id", resourceId);
        sysRoleResourceMapper.delete(qw);
    }

    /**
     * 校验父资源是否存在
     * @author lz
     * @since 2019/5/9 9:19
     */
    @Override
    public boolean parentIsExists(Long id) {
        QueryWrapper<SysResource> qw = new QueryWrapper<>();
        qw.eq("parent_id", id);
        int count = baseMapper.selectCount(qw);
        return count > 0;
    }

    /**
     * 获取vue动态菜单接口
     * @author lz
     * @since 2019/5/9 9:19
     */
    @Override
    public List<SysResource> menuTree() {
        QueryWrapper<SysResource> qw = new QueryWrapper<>();
        qw.eq("type",ResourceType.MENU);
        qw.orderByAsc("deep","seq");
        List<SysResource> sysResources = this.list(qw);
        List<SysResource> sysResourceList = new ArrayList<>();
        Map<Long, SysResource> dataMap = new HashMap<>(sysResources.size());
        if(CollectionUtils.isNotEmpty(sysResources)){
            sysResources.forEach(r -> {
                SysResource parent = dataMap.get(r.getParentId());
                if (parent == null) {
                    sysResourceList.add(r);
                } else {
                    parent.addChild(r);
                }
                dataMap.put(r.getId(), r);
            });
        }
        return sysResourceList;
    }

    /**
     * 获取父资源数据树
     * @author lz
     * @since 2019/5/9 9:19
     */
    @Override
    public Set<Tree> parentTree() {
        QueryWrapper<SysResource> qw = new QueryWrapper<>();
        qw.eq("type",ResourceType.MENU );
        qw.orderByAsc("deep", "seq");
        List<SysResource> sysResources = this.list(qw);
        Map<Long, List<SysResource>> map = sysResources.stream().collect(Collectors.groupingBy(SysResource::getParentId));
        SortedSet<Tree> treeList = new TreeSet<>();
        Tree node = new Tree("0", "0", "无",null);
        treeList.add(node);
        treeList.addAll(createCascaderNodeTree(0L, map));
        return treeList;
    }

    private Set<Tree> createCascaderNodeTree(Long pid, Map<Long, List<SysResource>> map) {
        Set<Tree> trees = Optional.ofNullable(
                map.get(pid)).orElseGet(() -> new ArrayList<>()).stream().filter( x -> x.getParentId().equals(pid)).map( x -> {
            Tree cascaderNode = new Tree(String.valueOf(x.getId()),
                    String.valueOf(x.getParentId()), x.getName(),
                    createCascaderNodeTree(x.getId(), map));
            return cascaderNode;}).collect(Collectors.toSet());
        return trees;
    }

}
