package com.le.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.le.system.entity.SysResource;
import com.le.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 资源Mapper
 * @author lz
 * @since 2019/5/9 9:19
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    Set<String> queryByRoleId(Long roleId);

    List<SysResource> findUserResourceList(@Param("user") SysUser user);
}
