package com.lamp.devops.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggingParamDto {
    private Map<String, String> fields;

    private LocalDateTime startTimestamp;
    private LocalDateTime endTimestamp;

    /**
     * 用于分页处理的参数
     */
    private Integer num;
    private Integer size;
}
