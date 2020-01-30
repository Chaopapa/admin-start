package com.le.sso.service;

import com.le.system.entity.SysToken;
import com.le.system.entity.SysUser;

/**
 * @author 严秋旺
 * @since 2019-04-28 17:35
 **/
public interface ISSOService {

    /**
     * 获取当前登录token
     *
     * @return SysToken
     */
    SysToken findLoginToken();

    /**
     * 获取当前登录的系统用户
     *
     * @return SysUser
     */
    SysUser findSystemLogin();

}
