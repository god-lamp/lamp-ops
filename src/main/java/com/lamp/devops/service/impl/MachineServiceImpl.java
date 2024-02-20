package com.lamp.devops.service.impl;

import com.lamp.devops.entity.Machine;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.mapper.MachineMapper;
import com.lamp.devops.service.IMachineService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 机器信息表 服务层实现。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Service
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements IMachineService {
    @Override
    public IPage<Machine> findAllMachines(Integer num, Integer size, String condition) {
        return null;
    }

    @Override
    public Long addMachine(Machine machine) {
        return null;
    }

    @Override
    public Boolean updateMachine(Long id, Machine machine) {
        return null;
    }

    @Override
    public Boolean deleteMachine(Long id) {
        return null;
    }

    @Override
    public void connMachine() {

    }
}
