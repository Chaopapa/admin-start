package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import com.le.system.entity.SysRole;
import com.le.system.entity.SysRoleResource;
import com.le.system.entity.SysUserRole;
import com.le.system.mapper.SysRoleMapper;
import com.le.system.mapper.SysRoleResourceMapper;
import com.le.system.mapper.SysUserRoleMapper;
import com.le.system.service.ISysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色接口实现层
 * @author lz
 * @since 2019/5/9 9:19
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleResourceMapper sysRoleResourceMapper;


    /**
     * 通过userId 后台角色分页
     * @author lz
     * @since 2019/5/9 9:19
     */
    @SuppressWarnings("unchecked")
    @Override
    public R findPage(Page<SysRole> pagination, SysRole search) {
        LambdaQueryWrapper<SysRole> qw = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(search.getName())) {
            qw.like(SysRole::getName, search.getName());
        }

        if (StringUtils.isNotBlank(search.getRole())) {
            qw.like(SysRole::getRole, search.getRole());
        }

        qw.orderByDesc(SysRole::getCreateDate);

        IPage<SysRole> page = this.page(pagination, qw);
        return R.success(page);
    }

    /**
     * 添加或修改角色
     *
     * @author lz
     * @since 2019/5/9 9:19
     * @param role resourceIds
     */
    @Override
    @Transactional
    public void editData(SysRole role, Long[] resourceIds) {
        Long id = role.getId();
        if (id != null) {
            deleteRoleResource(id);
            updateById(role);
        } else {
            save(role);
            id = role.getId();
        }
        if (ArrayUtils.isNotEmpty(resourceIds)) {
            for (Long permId : resourceIds) {
                SysRoleResource srr = new SysRoleResource();
                srr.setResourceId(permId);
                srr.setRoleId(id);
                sysRoleResourceMapper.insert(srr);
            }
        }
    }

    /**
     * 删除角色
     *
     * @param ids 用户Ids
     */
    @Override
    @Transactional
    public void del(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            for (Long id : ids) {
                deleteRoleResource(id);
                delUserRole(id);
            }
            removeByIds(ids);
        }
    }

    private void delUserRole(Long roleId) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        sysUserRoleMapper.delete(qw);
    }

    private void deleteRoleResource(Long roleId) {
        QueryWrapper<SysRoleResource> qw = new QueryWrapper<>();
        qw.eq("role_id", roleId);
        sysRoleResourceMapper.delete(qw);
    }

    /**
     * 查询用户角色列表
     *
     * @param userId 用户ID
     */
    @Override
    public List<SysRole> findUserRole(long userId) {
        return baseMapper.findUserRole(userId);
    }

    /**
     * 查询角色标识是否重复
     *
     * @param id
     * @param role
     */
    @Override
    public boolean exists(Long id, String role) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();

        if (id != null) {
            wrapper.ne(SysRole::getId, id);
        }

        wrapper.eq(SysRole::getRole, role);
        Integer count = baseMapper.selectCount(wrapper);
        return count != null && count > 0;
    }
}
