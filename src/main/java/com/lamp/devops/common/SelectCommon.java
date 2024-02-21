package com.lamp.devops.common;

import cn.hutool.core.util.StrUtil;
import com.lamp.devops.utils.ParamUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * @author god-lamp
 * @since 2024-02-20
 * 查询数据时用到的共同体
 */
@Log4j2
public class SelectCommon<T> {
    /**
     * 分页查询所有信息
     *
     * @param page      页码
     * @param size      每页大小
     * @param condition 查询条件
     * @param service   查询实体
     * @return 分页对象
     */
    public Page<T> findAll(Integer page, Integer size, String condition, IService<T> service) {
        QueryWrapper wrapper = QueryWrapper.create().where("1 = 1");
        if (StrUtil.isNotBlank(condition)) {
            Map<String, Object> map = ParamUtil.split(condition);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                wrapper.and(new QueryColumn(entry.getKey()).like(entry.getValue()));
            }
            log.info("构造的查询条件: {}", wrapper);
        }
        return service.page(new Page<>(page, size), wrapper);
    }
}
