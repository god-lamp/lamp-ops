package com.lamp.devops.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;


/**
 *  表定义层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public class SysAccountRoleTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SysAccountRoleTableDef SYS_ACCOUNT_ROLE = new SysAccountRoleTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    
    public final QueryColumn ACCOUNT_ID = new QueryColumn(this, "account_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ACCOUNT_ID, ROLE_ID};

    public SysAccountRoleTableDef() {
        super("", "sys_account_role");
    }

}
