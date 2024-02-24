package com.lamp.devops.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  实体类。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限编码
     */
    private String code;

    private String url;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 类型
     */
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
    private Integer hidden;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 子菜单
     */
    @Column(ignore = true)
    private List<SysMenu> children;

}
