package com.le.sso.handler;

import com.le.core.rest.R;
import com.le.core.rest.RCode;
import com.le.web.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 严秋旺
 * @since 2019-04-28 0:01
 **/
@Slf4j
@RestControllerAdvice
@Order(1)
public class SecurityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public R handleAuthenticationException(AuthenticationException e) {
        log.warn("认证失败，" + e.getMessage());
        log.debug("认证失败，" + e.getMessage(), e);
        return new R(RCode.accountOrPasswordError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e) {
        String url = HttpContextUtils.getRequestUrl();
        log.warn("越权访问，{}，{}", e.getMessage(), url);

        if (log.isDebugEnabled()) {
            log.debug("越权访问，" + e.getMessage() + "，" + url, e);
        }

        return new R(RCode.accessDenny);
    }

}
