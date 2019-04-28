package com.le.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.base.Tree;
import com.le.core.rest.R;
import com.le.system.entity.SysResource;
import com.le.system.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * @ClassName ISysUserService
 * @Author lz
 * @Description 资源接口层
 * @Date 2018/10/9 11:32
 * @Version V1.0
 **/
public interface ISysResourceService extends IService<SysResource> {

    /**
     * 根节点id
     */
    Long ROOT_ID = 0L;

    /**
     * @param sysResource
     * @return com.le.base.util.R
     * @description 添加或修改资源
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    R editData(SysResource sysResource);

    /**
     * @param
     * @return Set<Tree>
     * @description 获取资源数据树
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    Set<Tree> tree();

    /**
     * @param
     * @return List<SysResource>
     * @description 角色id查询角色的所有权限
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    List<SysResource> queryByRoleId(Long roleId);

    /**
     * @param permission
     * @return boolean
     * @description 校验资源是否存在
     * @author lz
     * @date 2018/10/11 17:11
     * @version V1.0.0
     */
    boolean permissionExists(String permission);

    /**
     * @param id
     * @return boolean
     * @description 删除资源
     * @author lz
     * @date 2018/10/11 17:11
     * @version V1.0.0
     */
    R del(Long id);

    /**
     * 获取用户菜单
     *
     * @param user 查询的用户
     * @return
     */
    List<SysResource> findUserTree(SysUser user);
}
