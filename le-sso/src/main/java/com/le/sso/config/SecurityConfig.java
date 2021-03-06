package com.le.sso.config;

import com.le.sso.filter.TokenAuthenticationFilter;
import com.le.sso.handler.AuthenticationHandler;
import com.le.sso.provider.SystemUserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 严秋旺
 * @since 2019-04-27 22:03
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationHandler authenticationHandler;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private SystemUserAuthenticationProvider systemUserAuthenticationProvider;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // we don't need CSRF because our token is invulnerable
        http.csrf().disable()
                .authorizeRequests().antMatchers("/auth/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .anyRequest().authenticated().and()
                // don't create se`ssion
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(authenticationHandler)
                .accessDeniedHandler(accessDeniedHandler).and()
                .authenticationProvider(systemUserAuthenticationProvider)
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

    }
}
