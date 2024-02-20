package com.lamp.devops.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public class SysRoleMenuTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SysRoleMenuTableDef SYS_ROLE_MENU = new SysRoleMenuTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");

    
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_ID, MENU_ID};

    public SysRoleMenuTableDef() {
        super("", "sys_role_menu");
    }

}
