package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import com.le.core.util.Constant;
import com.le.system.entity.SysUser;
import com.le.system.entity.SysUserRole;
import com.le.system.entity.vo.SysUserVo;
import com.le.system.mapper.SysUserMapper;
import com.le.system.service.ISysLogService;
import com.le.system.service.ISysUserRoleService;
import com.le.system.service.ISysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName SysUserServiceImpl
 * @Author lz
 * @Description 用户接口实现层
 * @Date 2018/10/9 11:33
 * @Version V1.0
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysLogService logService;

    /**
     * @param username 账号
     * @return com.le.admin.entity.SysUser
     * @description 通过账号查找后台登良者
     * @author lz
     * @date 2018/10/11 10:39
     * @version V1.0.0
     */
    @Override
    public SysUser findByUsername(String username) {
        LambdaQueryWrapper<SysUser> lq = new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getUsername, username);
        SysUser user = this.getOne(lq);
        return user;
    }

    /**
     * @param roles 角色id user
     * @return com.le.base.util.R
     * @description 添加或修改用户
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editData(SysUser user, List<Long> roles) {
        Long userId = user.getId();
        String password = user.getPassword();

        if (userId == null) {
            user.setPassword(DigestUtils.md5Hex(password));
            user.setStatus(0);
            save(user);
            sysUserRoleService.replaceUserRole(user, roles);
        } else {
            if (StringUtils.isBlank(password)) {
                user.setPassword(null);
            } else {
                user.setPassword(DigestUtils.md5Hex(password));
            }

            updateById(user);
            sysUserRoleService.replaceUserRole(user, roles);
        }
    }

    @Cacheable("sso:permission")
    @Override
    public List<String> findPermission(Long userId) {
        if (Constant.SUPER_ADMIN.equals(userId)) {
            return baseMapper.selectAllPermission();
        } else {
            return baseMapper.selectPermission(userId);
        }
    }

    @Cacheable("sso:role")
    @Override
    public List<String> findRole(Long userId) {
        if (Constant.SUPER_ADMIN.equals(userId)) {
            return baseMapper.selectAllRole();
        } else {
            return baseMapper.selectRole(userId);
        }
    }

    @Override
    public boolean usernameExists(Long id, String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        if (id != null) {
            wrapper.ne(SysUser::getId, id);
        }

        wrapper.eq(SysUser::getUsername, username);
        Integer count = baseMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Transactional
    @Override
    public void del(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            for (Long id : ids) {
                delUserRole(id);
            }
            removeByIds(ids);
        }
    }

    private void delUserRole(Long userId) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        sysUserRoleService.remove(qw);
    }

    @Override
    public void resetPassword(Long id) {
        this.updatePassword(id, "123456");
    }

    @Override
    public R findPage(Page<SysUserVo> page, SysUser search) {
        if (StringUtils.isNotEmpty(search.getUsername())) {
            search.setUsername("%" + search.getUsername() + "%");
        }

        if (StringUtils.isNotEmpty(search.getPhone())) {
            search.setPhone("%" + search.getPhone() + "%");
        }

        if (StringUtils.isNotEmpty(search.getName())) {
            search.setName("%" + search.getName() + "%");
        }

        List<SysUserVo> list = baseMapper.selectUserWithRole(page, search);
        page.setRecords(list);
        return R.success(page);
    }

    @Override
    public int updatePassword(Long id, String password) {
        String newPassword = DigestUtils.md5Hex(password);
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(newPassword);
        int rs = baseMapper.updateById(user);
        return rs;
    }
}
