package com.le.web.handler;

import com.le.core.exception.LEException;
import com.le.core.rest.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @ClassName LEExceptionHandler
 * @Author lz
 * @Description //异常处理器
 * @Date 2018/10/16 10:48
 * @Version V1.0
 **/
@Slf4j
@RestControllerAdvice
public class LEExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(LEException.class)
    public R handleRRException(LEException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public R handleRRException(BindException e) {
        return R.error(e.getBindingResult());
    }

    /**
     * @param e 异常
     * @return R
     * @description 系统异常处理
     * @author lz
     * @date 2018/10/16 10:29
     * @version V1.0.0
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        //Hibernate Validator验证消息返回
        BindingResult result = null;
        StringBuilder errorMsg = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException) {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            result = ((BindException) e).getBindingResult();
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            for (ConstraintViolation<?> violation : constraintViolations) {
                errorMsg.append(violation.getMessage()).append(",");
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return R.error(errorMsg.toString());
        }
        if (result != null) {
            for (ObjectError error : result.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(",");
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return R.error(errorMsg.toString());
        }
        return R.error(e.getMessage());
    }

    /**
     * 数据库异常
     *
     * @param e DataAccessException.class
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    public R runtimeExceptionHandler(DataAccessException e) {
        log.error("数据库异常", e);
        return R.error("数据库异常");
    }

    /**
     * @param e 异常
     * @return R
     * @description 系统异常处理
     * @author lz
     * @date 2018/10/16 10:29
     * @version V1.0.0
     */
    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(RuntimeException e) {
        log.error("未知异常", e);
        return R.error(e.getMessage());
    }

}
