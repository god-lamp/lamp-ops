package com.lamp.devops.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机器信息表 实体类。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Machine implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 代理id
     */
    private Long proxyId;

    /**
     * 秘钥id
     */
    private Long keyId;

    /**
     * 主机ip
     */
    private String machineHost;

    /**
     * ssh端口
     */
    private Integer sshPort;

    /**
     * 机器名称
     */
    private String machineName;

    /**
     * 机器唯一标识
     */
    private String machineTag;

    /**
     * 机器描述
     */
    private String description;

    /**
     * 机器账号
     */
    private String username;

    /**
     * 机器密码
     */
    private String password;

    /**
     * 机器认证方式 1: 密码认证 2: 独立秘钥
     */
    private Boolean authType;

    /**
     * 机器状态 1有效 2无效
     */
    private Boolean machineStatus;

    /**
     * 是否删除 1未删除 2已删除
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

}
