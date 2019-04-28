package com.le.sso.controller;

import com.le.core.rest.R;
import com.le.sso.authentication.SystemUserAuthenticationToken;
import com.le.system.entity.SysToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统用户登录
 *
 * @author 严秋旺
 * @since 2019-04-28 9:22
 **/
@RestController
@RequestMapping("/auth")
public class SystemUserAuthenticationContreoller {
    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("system-login")
    public R login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SystemUserAuthenticationToken authenticationToken = new SystemUserAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SysToken details = (SysToken) authentication.getDetails();
        return R.success().putData("token", details.getToken());
    }

    @RequestMapping("logout")
    public R logout(){
        SecurityContextHolder.clearContext();
        return R.success();
    }
}
