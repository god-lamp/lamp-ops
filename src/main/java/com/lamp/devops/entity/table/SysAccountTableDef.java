package com.lamp.devops.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public class SysAccountTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SysAccountTableDef SYS_ACCOUNT = new SysAccountTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    
    public final QueryColumn ICON = new QueryColumn(this, "icon");

    
    public final QueryColumn LOCK = new QueryColumn(this, "lock");

    
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    
    public final QueryColumn ENABLE = new QueryColumn(this, "enable");

    
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    
    public final QueryColumn REAL_NAME = new QueryColumn(this, "real_name");

    
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    
    public final QueryColumn LOGIN_TIME = new QueryColumn(this, "login_time");

    
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USERNAME, PASSWORD, ICON, EMAIL, TYPE, REAL_NAME, REMARK, CREATE_TIME, UPDATE_TIME, LOGIN_TIME, ENABLE, LOCK};

    public SysAccountTableDef() {
        super("", "sys_account");
    }

}
