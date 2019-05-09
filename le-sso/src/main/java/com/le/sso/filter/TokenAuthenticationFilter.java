package com.le.sso.filter;

import com.le.core.util.Constant;
import com.le.sso.authentication.SystemUserAuthenticationToken;
import com.le.system.entity.SysToken;
import com.le.system.entity.enums.TokenType;
import com.le.system.service.ISysTokenService;
import com.le.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 严秋旺
 * @since 2019-04-28 0:02
 **/

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private ISysTokenService tokenService;
    @Autowired
    private ISysUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("token");

        if (token == null) {
            token = request.getParameter("token");
        }

        SysToken sysToken = tokenService.findToken(token);

        if (sysToken != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            List<String> permissions = userService.findPermission(sysToken.getUserId());
            List<String> roles = userService.findRole(sysToken.getUserId());

            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }

            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            if (sysToken.getTokenType() == TokenType.ADMIN) {
                authorities.add(new SimpleGrantedAuthority(Constant.ROLE_ADMIN));
            }

            SystemUserAuthenticationToken authenticationToken = new SystemUserAuthenticationToken(sysToken.getUserId(), null, authorities);
            authenticationToken.setDetails(sysToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}
