package com.lamp.devops.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 机器信息表 表定义层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public class MachineTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 机器信息表
     */
    public static final MachineTableDef MACHINE = new MachineTableDef();

    /**
     * id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 秘钥id
     */
    public final QueryColumn KEY_ID = new QueryColumn(this, "key_id");

    /**
     * 是否删除 1未删除 2已删除
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    /**
     * 代理id
     */
    public final QueryColumn PROXY_ID = new QueryColumn(this, "proxy_id");

    /**
     * ssh端口
     */
    public final QueryColumn SSH_PORT = new QueryColumn(this, "ssh_port");

    /**
     * 机器认证方式 1: 密码认证 2: 独立秘钥
     */
    public final QueryColumn AUTH_TYPE = new QueryColumn(this, "auth_type");

    /**
     * 机器密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 机器账号
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 机器唯一标识
     */
    public final QueryColumn MACHINE_TAG = new QueryColumn(this, "machine_tag");

    /**
     * 修改时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 机器描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 主机ip
     */
    public final QueryColumn MACHINE_HOST = new QueryColumn(this, "machine_host");

    /**
     * 机器名称
     */
    public final QueryColumn MACHINE_NAME = new QueryColumn(this, "machine_name");

    /**
     * 机器状态 1有效 2无效
     */
    public final QueryColumn MACHINE_STATUS = new QueryColumn(this, "machine_status");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PROXY_ID, KEY_ID, MACHINE_HOST, SSH_PORT, MACHINE_NAME, MACHINE_TAG, DESCRIPTION, USERNAME, PASSWORD, AUTH_TYPE, MACHINE_STATUS, DELETED, CREATE_TIME, UPDATE_TIME};

    public MachineTableDef() {
        super("", "machine");
    }

}
