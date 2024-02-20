package com.lamp.devops.lang;

import lombok.Getter;

/**
 * @author god-lamp
 * @since 2023-12-28
 */
@Getter
public enum RespCode {
    REPEAT_SUBMIT_ERROR(-1, "您的请求已提交，请不要重复提交或等待片刻再尝试。"),
    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功"),
    /**
     * 请求失败
     */
    BAD_REQUEST(400, "请求失败"),
    /**
     * 认证失败/登录失效
     */
    UNAUTHORIZED(401, "认证失败/登录失效"),
    /**
     * 没有权限
     */
    FORBIDDEN(403, "没有权限"),
    /**
     * 请求资源不存在
     */
    NOT_FOUND(404, "请求资源不存在"),
    /**
     * 请求方式不允许
     */
    METHOD_NOT_ALLOWED(405, "请求方式不允许"),
    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(408, "请求超时"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    /**
     * 远程服务器接收到了一个无效的响应
     */
    BAD_GATEWAY(502, "远程服务器接收到了一个无效的响应"),
    /**
     * 网关响应超时
     */
    GATEWAY_TIMEOUT(504, "网关响应超时"),
    /**
     * 自定义异常信息
     */
    CUSTOM_EXCEPTION(-1, ""),
    ;

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 响应消息
     */
    private final String message;

    RespCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
