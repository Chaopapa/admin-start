package com.le.web.config.listener;

import com.le.core.util.Constant;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * @author 严秋旺
 * @since 2019-04-15 11:43
 **/
@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        Constant.CONTEXT_PATH = servletContext.getContextPath();
        Constant.APP_DIR = servletContext.getRealPath("/");

        if (new File(Constant.APP_DIR + "/WEB-INF/classes").exists()) {
            Constant.CLASS_DIR = Constant.APP_DIR + "/WEB-INF/classes";
        } else {
            Constant.CLASS_DIR = Constant.APP_DIR;
        }
    }
}
