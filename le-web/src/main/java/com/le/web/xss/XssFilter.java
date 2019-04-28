package com.le.web.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-01 10:20
 */

/**
 * @ClassName XSS过滤
 * @Author lz
 * @Description XSS过滤 过滤器配置類
 * @Date 2018/10/8 13:50
 * @Version V1.0
 **/
public class XssFilter implements Filter {
    private List<String> accessList = new ArrayList<>();

    @Override
    public void init(FilterConfig config) throws ServletException {
        String contextPath = config.getServletContext().getContextPath();
        accessList.add(contextPath + "/admin/config/print");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();

        for (String access : accessList) {
            if (uri.startsWith(access)) {
                chain.doFilter(request, response);
                return;
            }
        }

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(req);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
    }

}