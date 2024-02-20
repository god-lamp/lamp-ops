package com.lamp.devops.service;

import com.lamp.devops.entity.Machine;
import com.lamp.devops.lang.IPage;
import com.mybatisflex.core.service.IService;

/**
 * 机器信息表 服务层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public interface IMachineService extends IService<Machine> {
    IPage<Machine> findAllMachines(Integer num, Integer size, String condition);

    Long addMachine(Machine machine);

    Boolean updateMachine(Long id, Machine machine);

    Boolean deleteMachine(Long id);

    void connMachine();
}
