package com.le.web.aspect;

import com.le.web.annotation.SystemLog;
import com.le.web.util.HttpContextUtils;
import com.le.web.util.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lz
 * @ClassName SysLogAspect
 * @description 系统日志，切面处理类
 * @Version V1.0
 * @since 2018/10/9 15:30
 **/
@Aspect
@Component
public class SysLogAspect {

//    @Autowired todo
//    private ISysLogService logService;

    @Pointcut("@annotation(com.le.web.annotation.SystemLog)")
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

        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
// todo save log        logService.asyncLog(url, ip, systemLog, time);
        return result;
    }

}
