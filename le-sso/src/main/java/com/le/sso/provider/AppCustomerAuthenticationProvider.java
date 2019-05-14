package com.le.sso.provider;

import com.alibaba.druid.util.StringUtils;
import com.le.cs.entity.CustomerService;
import com.le.cs.service.ICustomerServiceService;
import com.le.sso.authentication.AppCustomerAuthenticationToken;
import com.le.sso.authentication.SystemUserAuthenticationToken;
import com.le.system.entity.SysToken;
import com.le.system.entity.enums.TokenType;
import com.le.system.service.ISysTokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppCustomerAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private ISysTokenService tokenService;
    @Autowired
    private ICustomerServiceService customerService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomerService user = customerService.findByUserName((String) authentication.getPrincipal());

        if (user == null) {
            throw new UsernameNotFoundException("登录名不存在：" + authentication.getName());
        }

        String password = (String) authentication.getCredentials();
        String encrypt = encrypt(password, user.getId());
        if (!encrypt.equals(user.getPassword())) {
            throw new BadCredentialsException("登录名或者密码错误");
        }

        SysToken token = tokenService.createToken(user.getId(), TokenType.BIZ);
        List<GrantedAuthority> authorities = new ArrayList<>();
        AppCustomerAuthenticationToken authenticationToken
                = new AppCustomerAuthenticationToken(token.getUserId(), null, authorities);
        authenticationToken.setDetails(token);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SystemUserAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public String encrypt(String password, Long id) {
        if (StringUtils.isEmpty(password)) {
            return null;
        }

        return DigestUtils.md5Hex(password.toLowerCase() + id);

    }
}
