package com.le.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.rest.R;
import com.le.system.entity.SysUser;
import com.le.system.entity.vo.SysUserVo;

import java.util.List;

/**
 * @ClassName ISysUserService
 * @Author lz
 * @Description 用户接口层
 * @Date 2018/10/9 11:32
 * @Version V1.0
 **/
public interface ISysUserService extends IService<SysUser> {

    /**
     * @param username 账号
     * @return com.le.admin.entity.SysUser
     * @description 通过账号查找后台登良者
     * @author lz
     * @date 2018/10/11 10:39
     * @version V1.0.0
     */
    SysUser findByUsername(String username);

    /**
     * @param roles 角色id user
     * @return com.le.base.util.R
     * @description 添加或修改用户
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    void editData(SysUser user, List<Long> roles);

    /**
     * 查找用户所有权限permission
     *
     * @param userId 用户id
     * @return 返回权限列表
     */
    List<String> findPermission(Long userId);

    /**
     * 查询用户权限
     *
     * @param userId 用户id
     * @return 返回角色列表
     */
    List<String> findRole(Long userId);

    /**
     * 查找用户账号是否存在
     *
     * @param username 用户id
     * @param username 用户账号
     * @return java.util.List<java.lang.String>
     */
    boolean usernameExists(Long id,String username);

    /**
     * 删除用户
     *
     * @param ids 用户ID
     */
    void del(List<Long> ids);

    /**
     * 重置密码 123456
     *
     * @param id 用户ID
     */
    void resetPassword(Long id);

    /**
     * @Description:用户管理分页
     * @Title:
     * @author
     */
    R findPage(Page<SysUserVo> page, SysUser search);

    /**
     * 修改用户密码
     *
     * @param id
     * @param password
     */
    int updatePassword(Long id, String password);

    /**
     * 后台用户登陆验证
     * @param sysUser
     * @return
     */
    R checkUser(SysUser sysUser);
}
