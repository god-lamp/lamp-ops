package com.lamp.devops.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
public class LoginInfo {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空!")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    private String password;
}
