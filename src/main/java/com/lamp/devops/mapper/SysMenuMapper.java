package com.lamp.devops.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.mybatisflex.core.BaseMapper;
import com.lamp.devops.entity.SysMenu;

/**
 *  映射层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

}
