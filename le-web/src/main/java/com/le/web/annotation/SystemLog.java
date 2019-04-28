package com.le.web.annotation;

import java.lang.annotation.*;

/**
 * @ClassName SystemLog
 * @Author lz
 * @Description 系统日志注解
 * @Date 2018/10/9 11:42
 * @Version V1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    String value();
}
