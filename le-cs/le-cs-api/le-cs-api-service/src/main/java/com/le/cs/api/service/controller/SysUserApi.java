package com.le.cs.api.service.controller;
import com.le.core.rest.R;
import com.le.core.util.JwtUtils;
import com.le.system.entity.SysUser;
import com.le.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sysUser",produces = "application/json;chartset=UTF-8")
public class SysUserApi {
    @Autowired
    ISysUserService sysUserService;


    @RequestMapping("login")
    public R login(SysUser sysUser){
        return sysUserService.checkUser(sysUser);
    }

    @RequestMapping("info")
    public  R  getUserInfo(String token){
        return  R.loginSuccess().putData("userInfo", JwtUtils.parseJWT(token));
    }

}
