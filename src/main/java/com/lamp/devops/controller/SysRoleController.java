package com.lamp.devops.controller;

import com.lamp.devops.entity.SysRole;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.model.dto.SysRoleDto;
import com.lamp.devops.service.ISysRoleService;
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
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private ISysRoleService roleService;

    /**
     * 添加。
     *
     * @param roleDto 角色信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/")
    public long save(@RequestBody SysRoleDto roleDto) {
        return roleService.addRole(roleDto);
    }

    /**
     * 根据主键删除。
     *
     * @param roleIds 主键列表
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/")
    public boolean remove(@RequestBody Set<Long> roleIds) {
        return roleService.delRole(roleIds);
    }

    /**
     * 根据主键更新。
     *
     * @param roleId  主键
     * @param roleDto 角色信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/{roleId}")
    public boolean update(@PathVariable Long roleId, @RequestBody SysRoleDto roleDto) {
        return roleService.updateRole(roleId, roleDto);
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
    public IPage<SysRole> page(Integer num, Integer size, String condition) {
        return roleService.findAllRoles(num, size, condition);
    }

}
