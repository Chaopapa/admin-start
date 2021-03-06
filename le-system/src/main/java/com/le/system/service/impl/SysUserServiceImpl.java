package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public R validate(HttpServletRequest request, String username, String password, boolean rememberMe) {
//        long start = System.currentTimeMillis();
//        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key());
//        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
//        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
//        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
//
//        Integer gt_server_status_code = (Integer) request.getSession()
//                .getAttribute(gtSdk.gtServerStatusSessionKey);
//        String userid = (String) request.getSession().getAttribute("userid");
//        int gtResult = 0;
//        String url = HttpContextUtils.getRequestUrl();
//        String ip = IPUtils.getIpAddr();
//
//        if (gt_server_status_code == null) {
//            logService.asyncLog(url, ip, null, username, null, "登录失败：会话超时", System.currentTimeMillis() - start);
//            return new R(RCode.sessionOutTime);
//        } else if (gt_server_status_code == 1) {
//            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
//        } else {
//            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
//        }
//
//        if (gtResult == 1) {
//            if (username != null && password != null) {
//                SysUser user = findByUsername(username);
//                if (user == null) {
//                    logService.asyncLog(url, ip, null, username, null, "登录失败：用户名错误", System.currentTimeMillis() - start);
//                    return R.error("用户名或者密码不正确!");
//                }
//                if (!DigestUtil.md5Hex(password).equals(user.getPassword())) {
//                    logService.asyncLog(url, ip, null, username, null, "登录失败：密码不正确", System.currentTimeMillis() - start);
//                    return R.error("用户名或者密码不正确!");
//                }
//                //使用权限工具进行用户登录，登录成功后跳到shiro配置的successUrl中，与下面的return没什么关系！
//                UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtil.md5Hex(password), rememberMe);
//                Subject subject = SecurityUtils.getSubject();
//                subject.login(token);
//                logService.asyncLog(url, ip, user.getId(), user.getUsername(), user.getName(), "登录成功", System.currentTimeMillis() - start);
//                return R.success();
//            } else {
//                return R.error("用户名或者密码不能为空!");
//            }
//        } else {
//            logService.asyncLog(url, ip, null, username, null, "登录失败：验证码错误", System.currentTimeMillis() - start);
//            return R.error("验证码错误!");
//        }
        return null;
    }

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
    public R editData(SysUser user, List<Long> roles) {
        Long userId = user.getId();

        if (userId == null) {
            String password = user.getPassword();
            if (StringUtils.isBlank(password)) {
                return R.error("请输入密码");
            }
            user.setPassword(DigestUtils.md5Hex(password));
            user.setStatus(0);
            save(user);
            sysUserRoleService.replaceUserRole(user, roles);
        } else {
            updateById(user);
            sysUserRoleService.replaceUserRole(user, roles);
        }
        return R.success();
    }

    /**
     * @param userId 用户id
     * @return java.util.List<java.lang.String>
     * @description rbac 查找用户所有权限permission
     * @author lz
     * @date 2018/10/11 10:44
     * @version V1.0.0
     */
    @Override
    public List<String> findAuthorities(Long userId) {
        return baseMapper.findAuthorities(userId);
    }

    /**
     * @param username 用户账号
     * @return java.util.List<java.lang.String>
     * @description rbac 查找用户账号是否存在
     * @author lz
     * @date 2018/10/11 10:44
     * @version V1.0.0
     */
    @Override
    public boolean usernameExists(String username) {
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("username", username);
        Integer count = baseMapper.selectCount(qw);
        return count > 0 ? true : false;
    }

    /**
     * @param ids
     * @return com.le.base.util.R
     * @description 删除用户
     * @author lz
     * @date 2018/10/11 10:14
     * @version V1.0.0
     */
    @Override
    public R del(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            for (Long id : ids) {
                delUserRole(id);
            }
            removeByIds(ids);
        }
        return R.success();
    }

    private void delUserRole(Long userId) {
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        sysUserRoleService.remove(qw);
    }

    @Override
    public R resetPassword(Long id) {
        SysUser user = baseMapper.selectById(id);

        if (user == null) {
            return R.error("用户不存在 ");
        }

        this.updatePassword(id, "123456");
        return R.success();
    }

    /**
     * @param page   分页参数
     * @param search SysUser 对象
     * @return com.le.base.util.R
     * @description 后台用户分页
     * @author lz
     * @date 2018/10/11 10:12
     * @version V1.0.0
     */
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

        List<SysUserVo> list = baseMapper.findSysRole(page, search);
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
//        ShiroUtil.clearAllCache(); todo
        return rs;
    }
}
