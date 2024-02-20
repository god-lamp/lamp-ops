package com.lamp.devops.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
public class IAuthenticationException extends AuthenticationException {
    private final String message;

    public IAuthenticationException(String msg) {
        super(msg);
        this.message = msg;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
