package com.le.admin.system;

import com.le.core.base.Tree;
import com.le.core.base.TreeNode;
import com.le.core.rest.R;
import com.le.system.entity.SysResource;
import com.le.system.service.ISysResourceService;
import com.le.web.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


/**
 * 资源后台管理
 * @author lz
 * @since 2019/5/9 9:19
 */
@Slf4j
@RestController
@RequestMapping("/admin/system/resource")
public class SysResourceController {

    @Autowired
    private ISysResourceService sysResourceService;

    /**
     * 获取资源数据
     *
     * @param id
     * @author lz
     */
    @RequestMapping("/manageData")
    @PreAuthorize("hasAuthority('sys:resource:view')")
    @SystemLog("查看资源详情")
    public R detail(Long id) {
        SysResource resource = sysResourceService.getById(id);
        return R.success().putData("resource", resource);
    }

    /**
     * 后台资源添加
     * @author lz
     * @since 2019/5/9 9:19
     */
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    @SystemLog("编辑资源信息")
    public R edit(@Valid SysResource sysResource) {
        boolean exist = sysResourceService.exists(sysResource.getId(), sysResource.getPermission());
        if (exist) {
            return R.error("权限标识重复");
        }
        sysResourceService.editData(sysResource);
        return R.success();
    }

    /**
     * 获取vue动态菜单接口
     * @author lz
     * @since 2019/5/9 9:19
     */
    @RequestMapping("/menuTree")
    public R menuTree() {
        List<SysResource> resources =  sysResourceService.menuTree();
        return R.success().putData("menus", resources);
    }

    /**
     * 获取资源下拉数据树
     * @author lz
     * @since 2019/5/9 9:19
     */
    @RequestMapping("/tree")
    public R permissionTree() {
        Set<TreeNode> trees = sysResourceService.tree();
        return R.success().putData("trees", trees);
    }

    /**
     * 获取父资源数据树
     * @author lz
     * @since 2019/5/9 9:19
     */
    @RequestMapping("/parentTree")
    public R parentTree() {
        Set<Tree> trees = sysResourceService.parentTree();
        return R.success().putData("parentTrees", trees);
    }

    /**
     * 校验权限
     * @author lz
     * @since 2019/5/9 9:19
     */
    @RequestMapping("/checkPermission")
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    public R checkPermission(Long id, String permission) {
        boolean exist = sysResourceService.exists(id, permission);
        return R.success().putData("exist", exist);
    }

    /**
     * 删除资源
     * @author lz
     * @since 2019/5/9 9:19
     */
    @RequestMapping("/del")
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    @SystemLog("删除资源")
    public R del(Long id) {
        boolean exist = sysResourceService.parentIsExists(id);
        if (exist) {
            return R.error("删除失败,请先删除子菜单");
        }
        sysResourceService.del(id);
        return R.success();
    }
}
