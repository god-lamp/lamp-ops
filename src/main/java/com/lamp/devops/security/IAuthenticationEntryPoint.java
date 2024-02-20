package com.lamp.devops.security;

import cn.hutool.json.JSONUtil;
import com.lamp.devops.lang.RespCode;
import com.lamp.devops.lang.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author god-lamp
 * @since 2024-02-20
 * 自定义返回结果：未登录或登录过期
 */
@Component
public class IAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println(JSONUtil.parse(Result.failed(RespCode.UNAUTHORIZED)));
        response.getWriter().flush();
    }
}
