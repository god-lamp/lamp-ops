package com.lamp.devops.controller;

import com.lamp.devops.entity.SysMenu;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.model.dto.SysMenuDto;
import com.lamp.devops.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 *  控制层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    private ISysMenuService menuService;

    /**
     * 添加。
     *
     * @param menuDto 菜单项信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/")
    public long save(@RequestBody SysMenuDto menuDto) {
        return menuService.addMenu(menuDto);
    }

    /**
     * 根据主键删除。
     *
     * @param menuIds 主键列表
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/")
    public boolean remove(@RequestBody Set<Long> menuIds) {
        return menuService.delMenu(menuIds);
    }

    /**
     * 根据主键更新。
     *
     * @param menuId  主键
     * @param menuDto 菜单项信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/{menuId}")
    public boolean update(@PathVariable Long menuId, @RequestBody SysMenuDto menuDto) {
        return menuService.updateMenu(menuId, menuDto);
    }

    /**
     * 分页查询。
     *
     * @param num       页码
     * @param size      每页大小
     * @param condition 查询条件
     * @return 分页对象
     */
    @GetMapping("/")
    public IPage<SysMenu> page(Integer num, Integer size, String condition) {
        return menuService.findAllMenus(num, size, condition);
    }

}