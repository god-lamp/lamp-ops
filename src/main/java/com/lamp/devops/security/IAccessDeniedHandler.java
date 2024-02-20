package com.lamp.devops.security;

import cn.hutool.json.JSONUtil;
import com.lamp.devops.lang.RespCode;
import com.lamp.devops.lang.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author god-lamp
 * @since 2024-02-20
 * 自定义返回结果：没有权限访问时响应错误
 */
@Component
public class IAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().println(JSONUtil.parse(Result.failed(RespCode.FORBIDDEN)));
        response.getWriter().flush();
    }
}