package com.lamp.devops.service;

import com.lamp.devops.entity.SysMenu;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.model.dto.SysMenuDto;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Set;

/**
 *  服务层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 查询所有菜单信息
     *
     * @param num       分页对象
     * @param size      关键信息
     * @param condition 查询条件
     * @return 分页对象
     */
    IPage<SysMenu> findAllMenus(Integer num, Integer size, String condition);

    /**
     * 根据用户名查询菜单信息
     *
     * @param username 用户名
     * @return 菜单信息
     */
    List<SysMenu> findMenuByUsername(String username);

    /**
     * 添加菜单
     *
     * @param menuDto 菜单信息
     * @return 菜单主键ID
     */
    long addMenu(SysMenuDto menuDto);

    /**
     * 更新菜单信息
     *
     * @param menuId  菜单ID
     * @param menuDto 菜单信息
     * @return true|false
     */
    boolean updateMenu(long menuId, SysMenuDto menuDto);

    /**
     * 批量删除菜单信息
     *
     * @param menuIds 菜单ID列表
     * @return true|false
     */
    boolean delMenu(Set<Long> menuIds);

    /**
     * 根据用户ID查询菜单项信息
     *
     * @param accountId 用户ID
     * @return 菜单项信息
     */
    List<SysMenu> findMenusByAccountId(Long accountId);
}
