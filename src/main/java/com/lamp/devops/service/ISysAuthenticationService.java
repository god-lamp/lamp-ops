package com.lamp.devops.service;

import com.lamp.devops.model.dto.LoginInfo;
import com.lamp.devops.model.dto.LoginResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
public interface ISysAuthenticationService extends UserDetailsService {
    /**
     * 用户登录请求
     *
     * @param info 登录输入信息
     * @return 登录成功的响应
     */
    String login(LoginInfo info);

    /**
     * 退出认证
     *
     * @param response 响应体
     * @return 响应消息
     */
    String logout(HttpServletResponse response);

    void verify(HttpServletRequest request, HttpServletResponse response);

    LoginResult info(Principal principal);
}
