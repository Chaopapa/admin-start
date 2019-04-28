package com.le.sso.handler;

import com.le.core.rest.R;
import com.le.core.rest.RCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 严秋旺
 * @since 2019-04-28 0:01
 **/
@Slf4j
@RestControllerAdvice
@Order(1)
public class SecurityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public R handleShiroAccountException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return new R(RCode.accountOrPasswordError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleShiroAccountException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return new R(RCode.accountOrPasswordError);
    }

}
