package com.lamp.devops.security;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import com.lamp.devops.lang.Constant;
import com.lamp.devops.utils.TokenProviderUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Log4j2
@Order(0)
@Component
public class IAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("servlet path: {}", request.getServletPath());
        boolean match = Constant.WHITES.stream().map(p -> new AntPathRequestMatcher(p.getValue(), p.getKey().toString())).anyMatch(matcher -> matcher.matches(request));
        log.info("match result : {}", match);
        if (match) {
            chain.doFilter(request, response);
            return;
        }

        Optional<UserDetails> maybeUserDetail = compoundAuth(request);
        if (maybeUserDetail.isPresent()) {
            UserDetails userDetail = maybeUserDetail.get();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities());
            auth.setDetails(userDetail);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        chain.doFilter(request, response);
    }

    private Optional<UserDetails> compoundAuth(HttpServletRequest request) {
        Pair<String, String> token = TokenProviderUtil.getAuthToken(request);
        if (StrUtil.equals(token.getKey(), TokenProviderUtil.JWT_AUTH_PREFIX)) {
            TokenProviderUtil.validate(token.getValue());
            String username = TokenProviderUtil.getAccount(token.getValue());
            return Optional.of(userDetailsService.loadUserByUsername(username));
        }
        if (StrUtil.equals(token.getKey(), TokenProviderUtil.BASIC_AUTH_PREFIX)) {
            Pair<String, String> userAndPassword = TokenProviderUtil.decodeBasicAuthToken(token.getValue());
            UserDetails userDetail = userDetailsService.loadUserByUsername(userAndPassword.getKey());
            if (!passwordEncoder.matches(userAndPassword.getValue(), userDetail.getPassword())) {
                throw new IllegalStateException("wrong user name or password");
            }
            return Optional.of(userDetail);
        }
        return Optional.empty();
    }
}
