package com.le.admin.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.core.rest.R;
import com.le.system.entity.SysRole;
import com.le.system.service.ISysResourceService;
import com.le.system.service.ISysRoleService;
import com.le.web.annotation.SystemLog;
import com.le.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色后台管理
 *
 * @author 严秋旺
 * @since 2019-4-29 22:11:6
 **/
@Slf4j
@RestController
@RequestMapping("/admin/system/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysResourceService sysResourceService;

    /**
     * 获取角色分页数据
     */
    @RequestMapping("/page")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:view')")
    @SystemLog("查看角色列表")
    public R page(SysRole search) {
        Page<SysRole> page = HttpContextUtils.getPage();
        return sysRoleService.findPage(page, search);
    }

    /**
     * 跳转角色信息页
     */
    @RequestMapping("/detail")
    @PreAuthorize("hasAuthority('sys:role:view')")
    @SystemLog("查看角色详情")
    public R detail(Long id) {
        SysRole role = sysRoleService.getById(id);

//            List<SysResource> sysResources = sysResourceService.queryByRoleId(id);
//            String resources = JsonUtils.toJson(sysResources);
//            model.put("resources", resources);
        return R.success().putData("role", role);
    }

    /**
     * 添加或者更新角色
     */
    @RequestMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @SystemLog("编辑角色信息")
    public R edit(SysRole role, Long[] resourceIds) {
        return sysRoleService.editData(role, resourceIds);
    }

    /**
     * 删除角色
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @SystemLog("删除角色")
    public R del(@RequestParam("ids") List<Long> ids) {
        return sysRoleService.del(ids);
    }
}
