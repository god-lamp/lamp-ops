package com.lamp.devops.controller;

import com.lamp.devops.entity.SysMenu;
import com.lamp.devops.model.SaveValidation;
import com.lamp.devops.model.UpdateValidation;
import com.lamp.devops.model.dto.SysMenuDto;
import com.lamp.devops.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 控制层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Slf4j
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
    @PostMapping()
    public long save(@RequestBody @Validated(SaveValidation.class) SysMenuDto menuDto) {
        log.info("menuDto: {}", menuDto);
        //return menuService.addMenu(menuDto);
        return 0L;
    }

    /**
     * 根据主键删除。
     *
     * @param menuIds 主键列表
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping()
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
    public boolean update(@PathVariable Long menuId, @RequestBody @Validated(UpdateValidation.class) SysMenuDto menuDto) {
        return menuService.updateMenu(menuId, menuDto);
    }

    /**
     * 分页查询。
     *
     * @param condition 查询条件
     * @return 分页对象
     */
    @Parameters({
            @Parameter(name = "condition", description = "查询条件")
    })
    @GetMapping()
    public List<SysMenu> page(@RequestParam(defaultValue = "", required = false) String condition) {
        return menuService.findAllMenus(condition);
    }
}