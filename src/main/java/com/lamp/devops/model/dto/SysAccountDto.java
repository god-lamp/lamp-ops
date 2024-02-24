package com.lamp.devops.model.dto;

import com.lamp.devops.model.SaveValidation;
import com.lamp.devops.model.UpdateValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
public class SysAccountDto {
    @NotNull(message = "用户名不能为空", groups = {SaveValidation.class, UpdateValidation.class})
    private String username;

    private String avatar;

    @Email(message = "邮箱格式不对!")
    @NotNull(message = "邮箱不能为空!")
    private String email;
    @NotNull(message = "用户类型不能为空", groups = {SaveValidation.class})
    private Integer type;

    private String realName;

    private String remark;
    @NotNull(message = "用户状态不能为空", groups = {SaveValidation.class})
    private Integer enable;
    @NotNull(message = "用户状态不能为空", groups = {SaveValidation.class})
    private Integer lock;

    @NotEmpty(message = "用户关联角色不能为空!", groups = {SaveValidation.class})
    private Set<Long> roleIds;
}
