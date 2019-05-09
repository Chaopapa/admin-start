package com.le.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.base.Tree;
import com.le.core.base.TreeNode;
import com.le.system.entity.SysResource;
import com.le.system.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * 资源接口层
 * @author lz
 * @since 2019/5/9 9:40
 */
public interface ISysResourceService extends IService<SysResource> {

    /**
     * 根节点id
     */
    Long ROOT_ID = 0L;

    /**
     * 添加或修改资源
     * @author lz
     * @since 2019/5/9 9:19
     */
    void editData(SysResource sysResource);

    /**
     * 获取资源下拉数据树
     * @author lz
     * @since 2019/5/9 9:19
     */
    Set<TreeNode> tree();

    /**
     * 角色id查询角色的所有权限
     * @author lz
     * @since 2019/5/9 9:19
     */
    List<SysResource> queryByRoleId(Long roleId);

    /**
     * 校验资源权限是否存在
     * @author lz
     * @since 2019/5/9 9:19
     */
    boolean exists(Long id, String permission);

    /**
     * 删除资源
     * @author lz
     * @since 2019/5/9 9:19
     */
    void del(Long id);

    /**
     * 获取父资源数据树
     * @author lz
     * @since 2019/5/9 9:19
     */
    Set<Tree> parentTree();

    /**
     * 校验父资源是否存在
     * @author lz
     * @since 2019/5/9 9:19
     */
    boolean parentIsExists(Long id);

    /**
     * 获取vue动态菜单接口
     * @author lz
     * @since 2019/5/9 9:19
     */
    List<SysResource> menuTree();

}
