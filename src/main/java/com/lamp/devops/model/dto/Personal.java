package com.lamp.devops.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author god-lamp
 * @since 2024-02-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Personal {
    private String username;
    private List<String> roles;
    private String avatar;
    private String introduction;
}
