package com.le.web.annotation;

import java.lang.annotation.*;

/**
 * @author 严秋旺
 * @since 2019-04-01 22:42
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FreemarkerSupport {
    /**
     * key名称
     *
     * @return
     */
    String value();
}
