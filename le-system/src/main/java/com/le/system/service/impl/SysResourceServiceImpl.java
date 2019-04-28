package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.base.Tree;
import com.le.core.rest.R;
import com.le.core.util.Constant;
import com.le.system.entity.SysResource;
import com.le.system.entity.SysRoleResource;
import com.le.system.entity.SysUser;
import com.le.system.entity.enums.ResourceType;
import com.le.system.mapper.SysResourceMapper;
import com.le.system.mapper.SysRoleResourceMapper;
import com.le.system.service.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName SysResourceServiceImpl
 * @Author lz
 * @Description 资源接口实现层
 * @Date 2018/10/9 11:33
 * @Version V1.0
 **/
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;

    /**
     * @param sysResource
     * @return com.le.base.util.R
     * @description 添加或修改资源
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    @Override
    public R editData(SysResource sysResource) {
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
        return R.success();
    }

    @Override
    public Set<Tree> tree() {
        QueryWrapper<SysResource> qw = new QueryWrapper<>();
        qw.orderByAsc("seq");
        List<SysResource> sysResources = this.list(qw);

        Set<Tree> trees = new LinkedHashSet<>();
        if (sysResources != null && !sysResources.isEmpty()) {
            sysResources.stream().forEach(s -> {
                Tree tree = new Tree();
                tree.setId(String.valueOf(s.getId()));
                tree.setpId(String.valueOf(s.getParentId()));
                tree.setName(s.getName());
                trees.add(tree);
            });
        }
        return trees;
    }

    @Override
    public List<SysResource> queryByRoleId(Long roleId) {
        return baseMapper.queryByRoleId(roleId);
    }

    @Override
    public boolean permissionExists(String permission) {
        QueryWrapper<SysResource> qw = new QueryWrapper<>();
        qw.eq("permission", permission);
        Integer count = baseMapper.selectCount(qw);
        if (count > 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public R del(Long id) {
        if (id != null) {
            SysResource r = this.getById(id);
            if (r == null) {
                return R.error("菜单已被删除");
            }
            if (parentIsExists(id)) {
                return R.error("删除失败,请先删除子菜单");
            }
            removeById(id);
            delRoleResourceByResourceId(id);
        }
        return R.success();
    }

    private void delRoleResourceByResourceId(Long resourceId) {
        QueryWrapper<SysRoleResource> qw = new QueryWrapper<>();
        qw.eq("resource_id", resourceId);
        sysRoleResourceMapper.delete(qw);
    }

    private boolean parentIsExists(Long id) {
        QueryWrapper<SysResource> qw = new QueryWrapper<>();
        qw.eq("parent_id", id);
        int count = baseMapper.selectCount(qw);
        return count > 0;
    }

    @Override
    public List<SysResource> findUserTree(SysUser user) {
        List<SysResource> list;
        List<SysResource> root = new ArrayList<>();

        if (user == null || Constant.SUPER_ADMIN.equals(user.getId())) {
            QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
            wrapper.eq("type", ResourceType.MENU.getValue());
            wrapper.orderByAsc("deep", "seq");
            list = baseMapper.selectList(wrapper);
        } else {
            list = baseMapper.findUserResourceList(user);
        }

        Map<Long, SysResource> map = new HashMap<>(list.size());

        list.forEach(resource -> {
            SysResource parent = map.get(resource.getParentId());


            if (parent == null) {
                root.add(resource);
            } else {
                parent.addChild(resource);
            }

            map.put(resource.getId(), resource);
        });

        return root;
    }
}
