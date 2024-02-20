package com.lamp.devops.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
public class PasswordDto {
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}