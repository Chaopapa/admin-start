package com.le.web.config.listener;

import com.le.core.util.Constant;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author 严秋旺
 * @since 2019-04-15 11:43
 **/
@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
//        Constant.CONTEXT_PATH = servletContext.getContextPath();
//        Constant.APP_PATH = servletContext.getRealPath("/");
//todo
    }
}
