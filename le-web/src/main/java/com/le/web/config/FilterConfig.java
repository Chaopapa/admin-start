package com.le.web.config;

import com.le.web.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter配置
 * @author lz
 * @since 2019/5/9 9:40
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
    /**
     * 跨域支持
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean CorsFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        Filter filter = new Filter() {
            @Override
            public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
            }

            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//                response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
                response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,token,sign");

                filterChain.doFilter(req, res);
            }

            @Override
            public void destroy() {
            }
        };
        registration.setFilter(filter);
        registration.setAsyncSupported(true);
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        return registration;
    }

}
