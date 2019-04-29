package com.le.sso.controller;

import com.le.core.rest.R;
import com.le.sso.authentication.SystemUserAuthenticationToken;
import com.le.sso.entity.vo.SystemUserLogin;
import com.le.sso.service.ISSOService;
import com.le.system.entity.SysToken;
import com.le.system.service.ISysTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 系统用户登录
 *
 * @author 严秋旺
 * @since 2019-04-28 9:22
 **/
@RestController
@RequestMapping("/auth")
public class UserAuthenticationContreoller {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ISSOService ssoService;
    @Autowired
    private ISysTokenService tokenService;

    /**
     * 系统用户登录
     *
     * @param userLogin
     * @return
     */
    @RequestMapping("system-login")
    public R login(@Valid SystemUserLogin userLogin) {
        SystemUserAuthenticationToken authenticationToken
                = new SystemUserAuthenticationToken(userLogin.getUsername(), userLogin.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SysToken details = (SysToken) authentication.getDetails();
        return R.success().putData("token", details.getToken());
    }

    /**
     * 用户退出
     *
     * @return
     */
    @RequestMapping("logout")
    public R logout() {
        SysToken token = ssoService.findLoginToken();
        tokenService.removeToken(token.getToken());
        SecurityContextHolder.clearContext();
        return R.success();
    }
}
