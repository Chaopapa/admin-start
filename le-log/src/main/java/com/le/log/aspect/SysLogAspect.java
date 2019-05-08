package com.le.log.aspect;

import com.le.log.annotation.SystemLog;
import com.le.sso.service.ISSOService;
import com.le.system.entity.SysUser;
import com.le.system.service.ISysLogService;
import com.le.web.util.HttpContextUtils;
import com.le.web.util.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 系统日志，切面处理类
 *
 * @author 严秋旺
 * @since 2018/10/9 15:30
 **/
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private ISysLogService logService;
    @Autowired
    private ISSOService ssoService;

    @Pointcut("@annotation(com.le.log.annotation.SystemLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        String url = HttpContextUtils.getRequestUrl();
        String ip = IPUtils.getIpAddr();

        SysUser login = ssoService.findSystemLogin();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        if (login == null) {
            logService.asyncLog(url, ip, null, null, null, systemLog.value(), time);
        } else {
            logService.asyncLog(url, ip, login.getId(), login.getUsername(), login.getName(), systemLog.value(), time);
        }

        return result;
    }

}
