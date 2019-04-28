package com.le.sso.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author 严秋旺
 * @since 2019-04-28 9:32
 **/
public class SystemUserAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public SystemUserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SystemUserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
