package com.lamp.devops.model.dto;

import com.lamp.devops.model.SaveValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
public class SysRoleDto {
    @NotBlank(message = "角色名称不能为空", groups = {SaveValidation.class})
    private String name;
    @NotBlank(message = "角色编码不能为空", groups = {SaveValidation.class})
    private String code;

    private String description;

    private Integer status;

    private Integer sort;
    @NotEmpty(message = "菜单项不能为空", groups = {SaveValidation.class})
    private Set<Long> menuIds;
}
