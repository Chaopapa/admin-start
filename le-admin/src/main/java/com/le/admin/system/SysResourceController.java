package com.le.admin.system;

import com.le.core.base.Tree;
import com.le.core.rest.R;
import com.le.system.entity.SysResource;
import com.le.system.service.ISysResourceService;
import com.le.web.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * @ClassName SysResourceController
 * @Author lz
 * 资源后台管理
 * @Date 2018/10/11 10:06
 * @Version V1.0
 **/
@Slf4j
@Controller
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
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:resource:view')")
    @SystemLog("查看资源详情")
    public R detail(Long id) {
        SysResource resource = sysResourceService.getById(id);
        return R.success().putData("resource", resource);
    }

    /**
     * @param sysResource
     * @return com.le.base.util.R
     * 后台资源
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    @RequestMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    @SystemLog("编辑资源信息")
    public R edit(SysResource sysResource) {
        return sysResourceService.editData(sysResource);
    }

    /**
     * @param
     * @return com.le.base.util.R
     * 获取资源数据树
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    @RequestMapping("/tree")
    @ResponseBody
    public R permissionTree() {
        Set<Tree> trees = sysResourceService.tree();
        return R.success().putData("trees", trees);
    }

    /**
     * 校验权限
     *
     * @return boolean 返回类型
     * @author lz
     */
    @RequestMapping("/checkPermission")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    public boolean checkPermission(String permission, String oPermission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        if (permission.equals(oPermission)) {
            return true;
        }
        return sysResourceService.permissionExists(permission);
    }

    /**
     * @return R 返回类型
     * 删除资源
     * @author lz
     */
    @RequestMapping("/del")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:resource:edit')")
    @SystemLog("删除资源")
    public R del(Long id) {
        return sysResourceService.del(id);
    }
}
