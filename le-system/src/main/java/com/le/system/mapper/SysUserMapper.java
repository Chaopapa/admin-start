package com.le.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.le.system.entity.SysUser;
import com.le.system.entity.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName SysUserMapper
 * @Author lz
 * @Description 用户Mapper
 * @Date 2018/10/9 11:35
 * @Version V1.0
 **/
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<String> selectPermission(Long userId);

    List<String> selectAllPermission();

    List<String> selectRole(Long userId);

    List<String> selectAllRole();

    List<SysUserVo> selectUserWithRole(Page<SysUserVo> page, @Param("search") SysUser search);

}
