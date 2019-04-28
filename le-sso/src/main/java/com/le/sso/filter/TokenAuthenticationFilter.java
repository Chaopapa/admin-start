package com.le.sso.filter;

import com.le.sso.authentication.SystemUserAuthenticationToken;
import com.le.system.entity.SysToken;
import com.le.system.service.ISysTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 严秋旺
 * @since 2019-04-28 0:02
 **/

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private ISysTokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("token");

        if (token == null) {
            token = request.getParameter("token");
        }

        SysToken sysToken = tokenService.findToken(token);

        if (sysToken != null) {
            SystemUserAuthenticationToken authenticationToken = new SystemUserAuthenticationToken(sysToken.getUserId(), null, new ArrayList<>());
            authenticationToken.setDetails(sysToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}
