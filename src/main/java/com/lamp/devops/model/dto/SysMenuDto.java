package com.lamp.devops.model.dto;

import com.lamp.devops.model.SaveValidation;
import com.lamp.devops.model.UpdateValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
public class SysMenuDto {
    @NotNull(message = "父ID不能为空!", groups = {SaveValidation.class, UpdateValidation.class})
    private Long parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {SaveValidation.class, UpdateValidation.class})
    private String name;

    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空", groups = {SaveValidation.class, UpdateValidation.class})
    private String code;

    private String url;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 类型
     */
    @NotNull(message = "菜单类型不能为空", groups = {SaveValidation.class, UpdateValidation.class})
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 外链地址
     */
    private String redirect;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 描述信息
     */
    private String description;
}
