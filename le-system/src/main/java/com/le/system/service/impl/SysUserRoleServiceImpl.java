package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.system.entity.SysUser;
import com.le.system.entity.SysUserRole;
import com.le.system.mapper.SysUserRoleMapper;
import com.le.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SysUserRoleServiceImpl
 * @Author lz
 * @Description 用户角色接口实现层
 * @Date 2018/10/9 11:33
 * @Version V1.0
 **/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Override
    @Transactional
    public void replaceUserRole(SysUser user, List<Long> roles) {
        QueryWrapper<SysUserRole> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("user_id", user.getId());
        baseMapper.delete(deleteWrapper);

        if (roles != null) {
            roles.forEach(roleId -> {
                SysUserRole userRole = new SysUserRole(user.getId(), roleId);
                baseMapper.insert(userRole);
            });
        }
    }

    @Override
    public ArrayList<Long> findRolesByUserId(Long id) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id).select("role_id");
        List<SysUserRole> sysUserRoleList = this.list(queryWrapper);
        System.out.println(sysUserRoleList.size());
        ArrayList<Long> roles = new ArrayList<>();
        for (int i=0;i<sysUserRoleList.size();i++){
            roles.add(sysUserRoleList.get(i).getRoleId());
        }
        return roles;
    }
}
