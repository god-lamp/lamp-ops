package com.lamp.devops.lang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author god-lamp
 * @since 2023-12-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private long code;
    private String message;
    private Object result;

    /**
     * 成功返回结果
     *
     * @param result 获取的数据
     */
    public static <T> Result<T> success(T result) {
        return new Result<>(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMessage(), result);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <Null> Result<Null> failed(String message) {
        return new Result<>(RespCode.CUSTOM_EXCEPTION.getCode(), message, null);
    }

    public static <Null> Result<Null> failed(RespCode resp) {
        return new Result<>(resp.getCode(), resp.getMessage(), null);
    }

    public static <Null> Result<Null> failed(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}
