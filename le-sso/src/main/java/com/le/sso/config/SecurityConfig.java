package com.le.sso.config;

import com.le.core.util.Constant;
import com.le.sso.filter.CorsFilter;
import com.le.sso.filter.TokenAuthenticationFilter;
import com.le.sso.handler.AuthenticationHandler;
import com.le.sso.provider.AppCustomerAuthenticationProvider;
import com.le.sso.provider.SystemUserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    private SystemUserAuthenticationProvider systemUserAuthenticationProvider;
    @Autowired
    private AppCustomerAuthenticationProvider appCustomerAuthenticationProvider;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private CorsFilter corsFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // we don't need CSRF because our token is invulnerable
        http.csrf().disable()
                .authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers("/api/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/admin/**").hasRole(Constant.ROLE_ADMIN)
                .anyRequest().authenticated().and()
                // don't create se`ssion
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(authenticationHandler)
//                .accessDeniedHandler(accessDeniedHandler)  //由全局异常处理
                .and()
                .authenticationProvider(appCustomerAuthenticationProvider)
                .authenticationProvider(systemUserAuthenticationProvider)
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
    }
}
