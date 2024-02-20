package com.lamp.devops.exception;

import com.lamp.devops.lang.RespCode;
import com.lamp.devops.lang.Result;
import jakarta.validation.constraints.Null;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Null> doException(Exception ex) {
        log.info("服务出现的异常:{}", ex.getMessage());
        ex.printStackTrace(System.err);
        return Result.failed(RespCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Null> doException(HttpMessageNotReadableException ex) {
        log.info("服务出现的异常:{}", ex.getMessage());
        return Result.failed("Http 消息不可读异常");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IAuthenticationException.class)
    public Result<Null> doException(IAuthenticationException ex) {
        log.info("认证失败异常:{}", ex.getMessage());
        return Result.failed(RespCode.UNAUTHORIZED.getCode(), ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public Result<Null> doIllegalStateException(IllegalStateException ex) {
        log.info("运行服务出现的IllegalStateException异常:{}", ex.getMessage());
        return Result.failed(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<Null> doIllegalStateException(IllegalArgumentException ex) {
        log.info("运行服务出现的IllegalArgumentException异常:{}", ex.getMessage());
        return Result.failed(ex.getMessage());
    }
}
