package com.le.web.config;

import com.le.web.annotation.FreemarkerSupport;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * @author lz
 * @since 2018/10/9 10:52
 **/
@Component
public class FreeMarkerConfig implements ApplicationContextAware, InitializingBean {

    @Autowired
    private Configuration configuration;
    @Autowired
    private ServletContext servletContext;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加上这句后，可以在页面上使用shiro标签
//        configuration.setSharedVariable("shiro", new ShiroTags());
        configuration.setSharedVariable("base", servletContext.getContextPath());
        configuration.setSharedVariable("baseRes", servletContext.getContextPath() + "/resources/v2");

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(FreemarkerSupport.class);
        SimpleHash leCore = new SimpleHash(configuration.getObjectWrapper());

        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object bean = entry.getValue();
            FreemarkerSupport freemarkerSupport = bean.getClass().getAnnotation(FreemarkerSupport.class);
            leCore.put(freemarkerSupport.value(), bean);
        }

        configuration.setSharedVariable("leCore", leCore);
        configuration.setObjectWrapper(new Java8ObjectWrapper(configuration.getVersion()));
    }
}
