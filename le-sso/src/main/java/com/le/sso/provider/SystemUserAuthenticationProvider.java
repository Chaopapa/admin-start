package com.le.sso.provider;

import com.le.core.util.Constant;
import com.le.sso.authentication.SystemUserAuthenticationToken;
import com.le.system.entity.SysToken;
import com.le.system.entity.SysUser;
import com.le.system.entity.enums.TokenType;
import com.le.system.service.ISysTokenService;
import com.le.system.service.ISysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 严秋旺
 * @since 2019-04-28 1:28
 **/
@Component
public class SystemUserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysTokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SysUser user = userService.findByUsername((String) authentication.getPrincipal());

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在：" + authentication.getName());
        }

        String password = (String) authentication.getCredentials();
        String md5 = DigestUtils.md5Hex(password);

        if (!md5.equals(user.getPassword())) {
            throw new BadCredentialsException("用户名或者密码错误");
        }

        SysToken token = tokenService.createToken(user.getId(), TokenType.ADMIN);
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> permissions = userService.findPermission(user.getId());
        List<String> roles = userService.findRole(user.getId());

        for (String permission : permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_"+Constant.ROLE_ADMIN));
        SystemUserAuthenticationToken authenticationToken
                = new SystemUserAuthenticationToken(token.getUserId(), null, authorities);
        authenticationToken.setDetails(token);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SystemUserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
