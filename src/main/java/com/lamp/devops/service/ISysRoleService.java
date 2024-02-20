package com.lamp.devops.service;

import com.lamp.devops.entity.SysRole;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.model.dto.SysRoleDto;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Set;

/**
 *  服务层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 查询所有角色信息
     *
     * @param num       分页对象
     * @param size      关键信息
     * @param condition 查询条件
     * @return 分页对象
     */
    IPage<SysRole> findAllRoles(Integer num, Integer size, String condition);

    /**
     * 根据角色名查询角色信息
     *
     * @param username 角色名
     * @return 角色信息
     */
    List<SysRole> findRoleByUsername(String username);

    /**
     * 根据角色ID查询角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    SysRole findRoleByUserId(long roleId);

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 角色主键ID
     */
    long addRole(SysRoleDto roleDto);

    /**
     * 更新角色信息
     *
     * @param roleId  角色ID
     * @param roleDto 角色信息
     * @return true|false
     */
    boolean updateRole(long roleId, SysRoleDto roleDto);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 角色ID列表
     * @return true|false
     */
    boolean delRole(Set<Long> roleIds);

    /**
     * 根据用户ID查询角色信息
     *
     * @param accountId 用户ID
     * @return 角色信息
     */
    Set<String> findRolesByAccountId(Long accountId);
}
