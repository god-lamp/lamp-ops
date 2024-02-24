package com.lamp.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.lamp.devops.entity.SysMenu;
import com.lamp.devops.mapper.SysMenuMapper;
import com.lamp.devops.model.dto.SysMenuDto;
import com.lamp.devops.service.ISysMenuService;
import com.lamp.devops.service.ISysRoleMenuService;
import com.lamp.devops.utils.ParamUtil;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lamp.devops.entity.table.SysAccountRoleTableDef.SYS_ACCOUNT_ROLE;
import static com.lamp.devops.entity.table.SysAccountTableDef.SYS_ACCOUNT;
import static com.lamp.devops.entity.table.SysMenuTableDef.SYS_MENU;
import static com.lamp.devops.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 * 服务层实现。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Resource
    private ISysRoleMenuService roleMenuService;

    @Override
    public List<SysMenu> findAllMenus(String condition) {
        QueryWrapper wrapper = QueryWrapper.create().where("1 = 1");
        if (StrUtil.isNotBlank(condition)) {
            Map<String, Object> map = ParamUtil.split(condition);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                wrapper.and(new QueryColumn(entry.getKey()).like(entry.getValue()));
            }
        }
        List<SysMenu> menus = this.list(wrapper);
        return menus.stream().filter(menu -> menu.getParentId().equals(0L)).map(menu -> covertMenuNode(menu, menus)).toList();
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
    public Map<Long, String> fetchParentMenuByType(Long type) {
        if (type > 3 || type < 2) {
            throw new IllegalStateException("传入的菜单类型不合法");
        }
        List<SysMenu> menus = this.list(QueryWrapper.create().where(SYS_MENU.TYPE.eq(type - 1)));

        return menus.stream().collect(Collectors.toMap(SysMenu::getId,SysMenu::getName));
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

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private SysMenu covertMenuNode(SysMenu menu, List<SysMenu> menus) {
        SysMenu node = new SysMenu();
        BeanUtils.copyProperties(menu, node);
        List<SysMenu> children = menus.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menus)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
