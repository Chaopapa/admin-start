package com.le.admin.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.core.rest.R;
import com.le.log.annotation.SystemLog;
import com.le.system.entity.SysRole;
import com.le.system.entity.SysUser;
import com.le.system.entity.vo.SysUserVo;
import com.le.system.service.ISysRoleService;
import com.le.system.service.ISysUserService;
import com.le.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


/**
 * 用户后台管理
 *
 * @author 严秋旺
 * @since 2018/9/30 9:20
 **/
@Slf4j
@RestController
@RequestMapping("/admin/system/user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * @Description: 获取用户分页数据
     * @Title: manage
     * @author lz
     */
    @RequestMapping("/page")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:view')")
    @SystemLog("查看用户列表")
    public R page(SysUser search) {
        Page<SysUserVo> page = HttpContextUtils.getPage();
        return sysUserService.findPage(page, search);
    }

    /**
     * 用户信息页
     */
    @RequestMapping("/detail")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @SystemLog("查看用户详情")
    public R detail(Long id) {
        SysUser user = sysUserService.getById(id);
        List<SysRole> userRole = sysRoleService.findUserRole(id);
        return R.success()
                .putData("user", user)
                .putData("userRole", userRole);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/roles")
    @PreAuthorize("hasAuthority('sys:user:view')")
    public R roles(Long id) {
        List<SysRole> roles = sysRoleService.list(null);
        return R.success()
                .putData("roles", roles);
    }

    /**
     * @Description: 添加或者更新用户
     * @Title: editData
     * @author lz
     */
    @RequestMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @SystemLog("编辑用户信息")
    public R edit(@Valid SysUser user, @RequestParam(value = "roleId", required = false) List<Long> roles) {
        if (user.getId() == null && StringUtils.isBlank(user.getPassword())) {
            return R.error("请输入密码");
        }

        boolean exist = sysUserService.usernameExists(user.getId(), user.getUsername());

        if (exist) {
            return R.error("登录名重复");
        }

        sysUserService.editData(user, roles);
        return R.success();
    }

    /**
     * 重置密码 123456
     */
    @RequestMapping(value = "/reset")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @SystemLog("重置用户密码")
    public R reset(Long id) {
        sysUserService.resetPassword(id);
        return R.success();
    }

    /**
     * 检查用户名
     *
     * @param id       用户id
     * @param username 登录名
     * @return
     */
    @RequestMapping(value = "/checkUserName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public R checkUserName(@RequestParam(required = false) Long id, String username) {
        boolean exist = sysUserService.usernameExists(id, username);
        return R.success().putData("exist", exist);
    }

    /**
     * 删除用户
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @SystemLog("删除用户")
    public R del(@RequestParam(value = "ids") List<Long> ids) {
        for (Long id : ids) {
            if (id <= 10) {
                return R.error("系统用户不能删除");
            }
        }

        sysUserService.del(ids);
        return R.success();
    }
}
