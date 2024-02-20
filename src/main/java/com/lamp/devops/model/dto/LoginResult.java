package com.lamp.devops.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    private String username;
    private String realName;
    private String email;
    private String remark;
    private String icon;
    private Set<String> roles;
}
