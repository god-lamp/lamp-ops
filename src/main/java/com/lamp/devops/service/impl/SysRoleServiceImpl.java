package com.lamp.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.lamp.devops.common.SelectCommon;
import com.lamp.devops.entity.SysRole;
import com.lamp.devops.entity.SysRoleMenu;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.mapper.SysRoleMapper;
import com.lamp.devops.model.dto.SysRoleDto;
import com.lamp.devops.service.ISysAccountRoleService;
import com.lamp.devops.service.ISysRoleMenuService;
import com.lamp.devops.service.ISysRoleService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lamp.devops.entity.table.SysAccountRoleTableDef.SYS_ACCOUNT_ROLE;
import static com.lamp.devops.entity.table.SysAccountTableDef.SYS_ACCOUNT;
import static com.lamp.devops.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;
import static com.lamp.devops.entity.table.SysRoleTableDef.SYS_ROLE;

/**
 *  服务层实现。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private ISysAccountRoleService accountRoleService;

    @Resource
    private ISysRoleMenuService roleMenuService;

    @Override
    public IPage<SysRole> findAllRoles(Integer page, Integer size, String condition) {
        return new IPage<>(new SelectCommon<SysRole>().findAll(page, size, condition, this));
    }

    @Override
    public Set<SysRole> findRoleByUsername(String username) {
        return new HashSet<>(this.list(
                QueryWrapper.create().select(SYS_ROLE.ALL_COLUMNS).from(SYS_ACCOUNT)
                        .leftJoin(SYS_ACCOUNT_ROLE).on(SYS_ACCOUNT_ROLE.ACCOUNT_ID.eq(SYS_ACCOUNT.ID))
                        .leftJoin(SYS_ROLE).on(SYS_ACCOUNT_ROLE.ROLE_ID.eq(SYS_ROLE.ID))
                        .where(SYS_ACCOUNT.USERNAME.eq(username))
        ));
    }

    @Override
    public Set<String> findRolesByAccountId(Long accountId) {
        List<SysRole> roles = this.list(
                QueryWrapper.create().select(SYS_ROLE.CODE).from(SYS_ROLE)
                        .leftJoin(SYS_ACCOUNT_ROLE).on(SYS_ACCOUNT_ROLE.ROLE_ID.eq(SYS_ROLE.ID))
                        .where(SYS_ACCOUNT_ROLE.ACCOUNT_ID.eq(accountId))
        );
        return roles.stream().map(SysRole::getCode).collect(Collectors.toSet());
    }

    @Override
    public SysRole findRoleByUserId(long roleId) {
        return this.getById(roleId);
    }

    @Override
    public long addRole(SysRoleDto roleDto) {
        SysRole role = BeanUtil.toBean(roleDto, SysRole.class);
        if (!this.save(role)) {
            throw new IllegalStateException("添加角色失败");
        }

        // 添加关联关系
        List<SysRoleMenu> roleMenus = roleDto.getMenuIds().stream().map(menuId -> SysRoleMenu.builder().roleId(role.getId()).menuId(menuId).build()).collect(Collectors.toList());
        if (!roleMenuService.saveBatch(roleMenus)) {
            throw new IllegalStateException("添加角色信息失败");
        }

        return role.getId();
    }

    @Override
    public boolean updateRole(long roleId, SysRoleDto roleDto) {
        SysRole role = BeanUtil.toBean(roleDto, SysRole.class, CopyOptions.create().ignoreNullValue());
        role.setId(roleId);
        if (!this.updateById(role)) {
            throw new IllegalStateException("修改角色失败");
        }

        // 修改角色和菜单项的关联关系
        if (CollUtil.isNotEmpty(roleDto.getMenuIds())) {
            if (!roleMenuService.remove(QueryWrapper.create().where(SYS_ROLE_MENU.ROLE_ID.eq(roleId)))) {
                throw new IllegalStateException("修改角色信息失败");
            }
            List<SysRoleMenu> roleMenus = roleDto.getMenuIds().stream().map(menuId -> SysRoleMenu.builder().roleId(roleId).menuId(menuId).build()).collect(Collectors.toList());
            if (!roleMenuService.saveBatch(roleMenus)) {
                throw new IllegalStateException("修改角色信息失败");
            }
        }
        return true;
    }

    @Override
    public boolean delRole(Set<Long> roleIds) {
        if (accountRoleService.exists(QueryWrapper.create().where(SYS_ACCOUNT_ROLE.ROLE_ID.in(roleIds)))) {
            List<SysRole> roles = this.list(QueryWrapper.create().select(SYS_ROLE.ALL_COLUMNS).from(SYS_ROLE).leftJoin(SYS_ACCOUNT_ROLE).on(SYS_ACCOUNT_ROLE.ROLE_ID.eq(SYS_ROLE.ID)).where(SYS_ACCOUNT_ROLE.ROLE_ID.in(roleIds)));
            throw new IllegalStateException(String.format("以下角色【%s】存在关联用户，不允许删除", roles.stream().map(SysRole::getName).collect(Collectors.toSet())));
        }
        if (!this.removeByIds(roleIds)) {
            throw new IllegalStateException("删除角色失败");
        }

        // 删除关联关系
        return roleMenuService.remove(QueryWrapper.create().where(SYS_ROLE_MENU.ROLE_ID.in(roleIds)));
    }
}
