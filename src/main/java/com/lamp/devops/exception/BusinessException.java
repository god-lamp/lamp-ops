package com.lamp.devops.exception;

import com.lamp.devops.lang.RespCode;
import lombok.Getter;

/**
 * @author god-lamp
 * @since 2024-02-20
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    public RespCode resultCode;

    public BusinessException(RespCode code) {
        super(code.getMessage());
        this.resultCode = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}