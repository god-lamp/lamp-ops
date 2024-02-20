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

    public static final List<Pair<HttpMethod, String>> WHITES = new LinkedList<>();

    static {
        WHITES.add(Pair.of(HttpMethod.POST, "/login"));
        WHITES.add(Pair.of(HttpMethod.POST, "/logout"));
        WHITES.add(Pair.of(HttpMethod.GET, "/verify"));

        WHITES.add(Pair.of(HttpMethod.GET, "/favicon.ico"));
        WHITES.add(Pair.of(HttpMethod.GET, "/swagger-ui.html"));
        WHITES.add(Pair.of(HttpMethod.GET, "/swagger-ui/**"));
        WHITES.add(Pair.of(HttpMethod.GET, "/v3/**"));
    }
}