package com.le.admin.system;

import com.le.core.rest.R;
import com.le.sso.service.ISSOService;
import com.le.system.entity.SysRole;
import com.le.system.entity.SysUser;
import com.le.system.service.ISysRoleService;
import com.le.system.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/profile")
public class ProfileController {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISSOService ssoService;

    @RequestMapping("info")
    public R info() {
        SysUser user = ssoService.findSystemLogin();
        user.setPassword(null);
        return R.success().putData("user", user);
    }

    /**
     * 个人主页
     */
    @RequestMapping({"/index", "/"})
    public String index(ModelMap model) {
        SysUser user = ssoService.findSystemLogin();
        model.addAttribute("user", user);

        List<SysRole> roles = roleService.findUserRole(user.getId());
        model.addAttribute("roles", roles);

        return "admin/profile/index";
    }

    @RequestMapping("/password")
    @ResponseBody
    public R password(String oldPassword, String password) {
        if (StringUtils.isEmpty(password)) {
            return R.error("密码为空");
        }

        SysUser user = ssoService.findSystemLogin();
        // todo 验证旧密码
        sysUserService.updatePassword(user.getId(), password);
        return R.success();
    }
}
