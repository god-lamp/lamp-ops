package com.lamp.devops.lang;

import cn.hutool.core.lang.Pair;
import org.springframework.http.HttpMethod;

import java.util.LinkedList;
import java.util.List;

/**
 * @author god-lamp
 * @since 2024-01-03
 */
public class Constant {
    public static final String INDEX_PREFIX = "sys-logging-";

    /**
     * 请求白名单，不需要通过授权就可以访问的接口
     */
    public static final List<Pair<HttpMethod, String>> WHITES = new LinkedList<>();
    /**
     * 认证列表，登录授权后可以访问的接口，不区分角色权限
     */
    public static final List<Pair<HttpMethod, String>> AUTHENTICATION_LIST = new LinkedList<>();

    static {
        WHITES.add(Pair.of(HttpMethod.POST, "/login"));
        WHITES.add(Pair.of(HttpMethod.POST, "/logout"));
        WHITES.add(Pair.of(HttpMethod.GET, "/verify"));

        WHITES.add(Pair.of(HttpMethod.GET, "/favicon.ico"));
        WHITES.add(Pair.of(HttpMethod.GET, "/swagger-ui.html"));
        WHITES.add(Pair.of(HttpMethod.GET, "/swagger-ui/**"));
        WHITES.add(Pair.of(HttpMethod.GET, "/v3/**"));

        // 添加认证列表数据
        AUTHENTICATION_LIST.add(Pair.of(HttpMethod.GET, "/info"));
    }
}