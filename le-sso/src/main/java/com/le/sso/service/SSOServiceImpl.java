package com.le.sso.service;

import com.le.system.entity.SysToken;
import com.le.system.entity.SysUser;
import com.le.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author 严秋旺
 * @since 2019-04-28 17:35
 **/
@Service
public class SSOServiceImpl implements ISSOService {

    @Autowired
    @Lazy
    private ISysUserService userService;

    @Override
    public SysToken findLoginToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysToken sysToken = (SysToken) authentication.getDetails();
        return sysToken;
    }

    @Override
    public SysUser findSystemLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysToken sysToken = (SysToken) authentication.getDetails();
        Long userId = sysToken.getUserId();
        SysUser user = userService.getById(userId);
        return user;
    }
}
