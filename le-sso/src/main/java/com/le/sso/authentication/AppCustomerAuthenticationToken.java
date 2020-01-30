package com.le.sso.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author 曾敏
 * @since 2019-05-14 15:36
 **/
public class AppCustomerAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public AppCustomerAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AppCustomerAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
