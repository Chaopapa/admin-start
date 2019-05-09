package com.le.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.rest.R;
import com.le.system.entity.SysRole;
import com.le.system.entity.SysUserRole;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ISysUserService
 * @Author lz
 * @Description 角色接口层
 * @Date 2018/10/9 11:32
 * @Version V1.0
 **/
public interface ISysRoleService extends IService<SysRole> {


    /**
     * 通过userId 后台角色分页
     * @author lz
     * @since 2019/5/9 9:19
     */
    R findPage(Page<SysRole> page, SysRole search);

    /**
     * 添加或修改角色
     *
     * @param role
     */
    void editData(SysRole role, Long[] resourceIds);


    /**
     * 删除角色
     *
     * @param ids 用户Ids
     */
    void del(List<Long> ids);

    /**
     * 查询用户角色列表
     *
     * @param userId 用户ID
     */
    List<SysRole> findUserRole(long userId);

    /**
     * 查询角色标识是否重复
     *
     * @param id
     * @param role
     */
    boolean exists(Long id, String role);
}
