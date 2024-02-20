package com.lamp.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.lamp.devops.common.SelectCommon;
import com.lamp.devops.entity.SysMenu;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.mapper.SysMenuMapper;
import com.lamp.devops.model.dto.SysMenuDto;
import com.lamp.devops.service.ISysMenuService;
import com.lamp.devops.service.ISysRoleMenuService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lamp.devops.entity.table.SysAccountRoleTableDef.SYS_ACCOUNT_ROLE;
import static com.lamp.devops.entity.table.SysAccountTableDef.SYS_ACCOUNT;
import static com.lamp.devops.entity.table.SysMenuTableDef.SYS_MENU;
import static com.lamp.devops.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 *  服务层实现。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Resource
    private ISysRoleMenuService roleMenuService;

    @Override
    public IPage<SysMenu> findAllMenus(Integer num, Integer size, String condition) {
        return new IPage<>(new SelectCommon<SysMenu>().findAll(num, size, condition, this));
    }

    @Override
    public List<SysMenu> findMenuByUsername(String username) {
        return this.list(
                QueryWrapper.create().select(SYS_MENU.ALL_COLUMNS).from(SYS_MENU)
                        .leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.ID))
                        .leftJoin(SYS_ACCOUNT_ROLE).on(SYS_ACCOUNT_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ID))
                        .leftJoin(SYS_ACCOUNT).on(SYS_ACCOUNT_ROLE.ACCOUNT_ID.eq(SYS_ACCOUNT.ID))
                        .where(SYS_ACCOUNT.USERNAME.eq(username))
        );
    }


    @Override
    public List<SysMenu> findMenusByAccountId(Long accountId) {
        return this.list(
                QueryWrapper.create().select(SYS_MENU.ALL_COLUMNS).from(SYS_MENU)
                        .leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.ID))
                        .leftJoin(SYS_ACCOUNT_ROLE).on(SYS_ACCOUNT_ROLE.ROLE_ID.eq(SYS_ROLE_MENU.ROLE_ID))
                        .where(SYS_ACCOUNT_ROLE.ACCOUNT_ID.eq(accountId))
        );
    }

    @Override
    public long addMenu(SysMenuDto menuDto) {
        SysMenu menu = BeanUtil.toBean(menuDto, SysMenu.class);
        if (!this.save(menu)) {
            throw new IllegalStateException("添加菜单项失败");
        }
        return menu.getId();
    }

    @Override
    public boolean updateMenu(long menuId, SysMenuDto menuDto) {
        SysMenu menu = BeanUtil.toBean(menuDto, SysMenu.class, CopyOptions.create().ignoreNullValue());
        menu.setId(menuId);
        if (!this.updateById(menu)) {
            throw new IllegalStateException("修改菜单项失败");
        }
        return true;
    }

    @Override
    public boolean delMenu(Set<Long> menuIds) {
        if (roleMenuService.exists(QueryWrapper.create().where(SYS_ROLE_MENU.MENU_ID.in(menuIds)))) {
            List<SysMenu> menus = this.list(QueryWrapper.create().select(SYS_MENU.ALL_COLUMNS).from(SYS_MENU).leftJoin(SYS_ROLE_MENU).on(SYS_ROLE_MENU.MENU_ID.eq(SYS_MENU.ID)).where(SYS_ROLE_MENU.MENU_ID.in(menuIds)));
            throw new IllegalStateException(String.format("以下菜单项【%s】存在关联角色，不允许删除", menus.stream().map(SysMenu::getName).collect(Collectors.toSet())));
        }
        if (!this.removeByIds(menuIds)) {
            throw new IllegalStateException("删除菜单项失败");
        }
        return true;
    }
}
