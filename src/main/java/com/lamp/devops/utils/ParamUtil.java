package com.lamp.devops.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author god-lamp
 * @since 2024-01-02
 */
public class ParamUtil {
    /**
     * 将请求参数转换为 map 对象
     *
     * @param param 带分隔的url参数
     * @return map对象
     */
    public static Map<String, Object> split(String param) {
        Map<String, Object> map = new HashMap<>();
        String[] params = param.split("&");
        for (String p : params) {
            String[] pair = p.split("=");
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            }
        }
        return map;
    }
}
