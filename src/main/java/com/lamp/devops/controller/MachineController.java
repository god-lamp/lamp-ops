package com.lamp.devops.controller;

import com.lamp.devops.entity.Machine;
import com.lamp.devops.service.IMachineService;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 机器信息表 控制层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@RestController
@RequestMapping("/machine")
public class MachineController {

    @Resource
    private IMachineService service;

    /**
     * 添加机器信息表。
     *
     * @param machine 机器信息表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Machine machine) {
        return service.save(machine);
    }

    /**
     * 根据主键删除机器信息表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return service.removeById(id);
    }

    /**
     * 根据主键更新机器信息表。
     *
     * @param machine 机器信息表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Machine machine) {
        return service.updateById(machine);
    }

    /**
     * 查询所有机器信息表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Machine> list() {
        return service.list();
    }

    /**
     * 根据机器信息表主键获取详细信息。
     *
     * @param id 机器信息表主键
     * @return 机器信息表详情
     */
    @GetMapping("getInfo/{id}")
    public Machine getInfo(@PathVariable Serializable id) {
        return service.getById(id);
    }

    /**
     * 分页查询机器信息表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Machine> page(Page<Machine> page) {
        return service.page(page);
    }

}
