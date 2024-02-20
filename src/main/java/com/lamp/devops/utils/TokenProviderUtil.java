package com.lamp.devops.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * @author god-lamp
 * @since 2024-01-03
 */
@Log4j2
public class TokenProviderUtil {
    public static final Integer OFFSET_HOUR = 6;
    public static final String JWT_AUTH_PREFIX = "Bearer ";
    public static final String BASIC_AUTH_PREFIX = "Basic ";
    private static final byte[] KEY = "1234567890".getBytes();

    public static String token(String username) {
        return JWT.create()
                // 设置签发时间
                .setIssuedAt(DateUtil.date())
                // 设置过期时间
                .setExpiresAt(DateUtil.offsetHour(DateUtil.date(), OFFSET_HOUR))
                // 设置签发时间
                .setNotBefore(DateUtil.date())
                .setIssuer("devil-idiots")
                .setSubject("traffic-lvs")
                .setJWTId(UUID.randomUUID().toString())
                .setPayload("username", username)
                .setKey(KEY)
                .sign();
    }

    public static void validate(String token) {
        JWTValidator.of(token)
                .validateAlgorithm(JWTSignerUtil.hs256(KEY))
                // leeway:容忍空间，单位：秒
                .validateDate(DateUtil.date(), 5);
    }

    public static String getAccount(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        return (String) jwt.getPayload("username");
    }

    /**
     * 解析登录时的 jwt token
     *
     * @param token token
     * @return pair
     */
    public static Pair<String, String> decodeBasicAuthToken(String token) {
        String plain = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        // split() 将提供的文本拆分为具有最大长度、指定分隔符的数组
        String[] userAndPassword = StringUtils.split(plain, ":", 2);
        if (userAndPassword.length != 2) {
            throw new IllegalStateException("bad authentication token");
        }
        return Pair.of(userAndPassword[0], userAndPassword[1]);
    }

    public static String resolve(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static Pair<String, String> getAuthToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        Assert.notNull(header, "请求头中不存在认证信息");
        // token验证
        if (StringUtils.startsWith(header, JWT_AUTH_PREFIX)) {
            return Pair.of(JWT_AUTH_PREFIX, StringUtils.removeStart(header, JWT_AUTH_PREFIX));
        }
        // 密码验证
        if (StringUtils.startsWith(header, BASIC_AUTH_PREFIX)) {
            return Pair.of(BASIC_AUTH_PREFIX, StringUtils.removeStart(header, BASIC_AUTH_PREFIX));
        }
        String[] seg = StringUtils.split(header, " ");
        if (seg != null && seg.length == 2) {
            return Pair.of(seg[0], seg[1]);
        }
        log.info("异常的认证Token [{}], {} => {} {}", header, request.getRemoteAddr(), request.getMethod(), request.getRequestURI());
        throw new IllegalStateException("异常的认证Token");
    }
}


