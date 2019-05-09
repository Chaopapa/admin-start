package com.le.log.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author 严秋旺
 * @since 2019-5-8 13:10:22
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String value();
}
