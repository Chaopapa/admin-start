package com.le.admin.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.core.rest.R;
import com.le.system.entity.SysUser;
import com.le.system.entity.vo.SysUserVo;
import com.le.system.service.ISysRoleService;
import com.le.system.service.ISysUserService;
import com.le.web.annotation.SystemLog;
import com.le.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * @Description: 跳转用户信息页
     * @Title: edit
     * @author lz
     */
    @RequestMapping("/detail")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @SystemLog("查看用户详情")
    public R detail(Long id) {
        SysUser user = sysUserService.getById(id);

//            Map<String, SysUserRole> roleMap = sysRoleService.findUserRoleMap(id);
//            model.put("roleMap", roleMap);

//        model.put("roles", sysRoleService.list(null));
        return R.success().putData("user", user);
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
    public R edit(SysUser user, @RequestParam(value = "roleId", required = false) List<Long> roles) {
        return sysUserService.editData(user, roles);
    }

    /**
     * @Description: 重置密码 123456
     * @Title: resetPassword
     * @author lz
     */
    @RequestMapping(value = "/reset")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @SystemLog("重置用户密码")
    public R reset(Long id) {
        return sysUserService.resetPassword(id);
    }

    /**
     * 检查用户名
     *
     * @param oldUsername
     * @param username
     * @return
     */
    @RequestMapping(value = "/checkUserName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public Map<String, Object> checkUserName(String oldUsername, String username) {
        boolean exist;

        if (StringUtils.isNotBlank(oldUsername) && oldUsername.equals(username)) {
            exist = false;
        } else {
            exist = sysUserService.usernameExists(username);
        }
        Map<String, Object> rest = new HashMap<>();
        rest.put("valid", !exist);
        return rest;
    }

    /**
     * @return R 返回类型
     * @Description: 删除用户
     * @author lz
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @SystemLog("删除用户")
    public R del(@RequestParam(value = "ids") List<Long> ids) {
        return sysUserService.del(ids);
    }
}
