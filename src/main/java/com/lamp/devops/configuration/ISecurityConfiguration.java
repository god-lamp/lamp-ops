package com.lamp.devops.configuration;

import cn.hutool.core.lang.Pair;
import com.lamp.devops.lang.Constant;
import com.lamp.devops.security.IAccessDeniedHandler;
import com.lamp.devops.security.IAuthenticationEntryPoint;
import com.lamp.devops.security.IAuthenticationManager;
import com.lamp.devops.security.IAuthenticationTokenFilter;
import com.lamp.devops.service.ISysAuthenticationService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ISecurityConfiguration {
    @Resource
    private IAccessDeniedHandler accessDeniedHandler;
    @Resource
    private IAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private ISysAuthenticationService authenticationService;

    @Resource
    private IAuthenticationManager authorizationManager;

    @Resource
    private IAuthenticationTokenFilter authenticationTokenFilter;

    //@Resource
    //private VerifyCodeFilter verifyCodeFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        for (Pair<HttpMethod, String> white : Constant.WHITES) {
            http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry.requestMatchers(white.getKey(), white.getValue()).permitAll());
        }

        // 动态权限处理
        http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry.anyRequest().access(authorizationManager));
        // 会话信息处理
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 错误处理
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler));

        // 将过滤器添加到 UsernamePasswordAuthenticationFilter 之前
        //http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * AuthenticationManager：负责认证的
     * DaoAuthenticationProvider：负责将 sysUserDetailsService、passwordEncoder融合起来送到AuthenticationManager中
     *
     * @param passwordEncoder 加密实例
     * @return 认证管理对象
     */
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authenticationService);
        // 关联使用的密码编码器
        provider.setPasswordEncoder(passwordEncoder);

        // 将provider放置进 AuthenticationManager 中,包含进去
        return new ProviderManager(provider);
    }
}
