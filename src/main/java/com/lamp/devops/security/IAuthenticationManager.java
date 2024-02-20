package com.lamp.devops.security;

import cn.hutool.core.util.StrUtil;
import com.lamp.devops.entity.SysAccount;
import com.lamp.devops.entity.SysMenu;
import com.lamp.devops.lang.Constant;
import com.lamp.devops.service.ISysMenuService;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.lamp.devops.entity.table.SysMenuTableDef.SYS_MENU;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Log4j2
@Component
public class IAuthenticationManager implements AuthenticationManager, AuthorizationManager<RequestAuthorizationContext> {

    private final UserDetailsService userDetailService;
    private final PasswordEncoder passwordEncoder;
    @Resource
    private ISysMenuService menuService;

    public IAuthenticationManager(UserDetailsService userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails ud = userDetailService.loadUserByUsername(authentication.getName());
        assert ud instanceof SysAccount;
        if (passwordEncoder.matches((String) authentication.getCredentials(), ud.getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(ud.getUsername(), ud.getPassword());
            auth.setDetails(ud);
            log.info("authentication: {}", auth);
            return auth;
        }
        return null;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        // 获取请求路径,获取HttpServletRequest
        HttpServletRequest request = requestAuthorizationContext.getRequest();

        if (Constant.WHITES.stream().map(path -> new AntPathRequestMatcher(path.getValue(), path.getKey().toString())).anyMatch(matcher -> matcher.matches(request))) {
            return new AuthorizationDecision(true);
        }
        String uri = request.getServletPath();
        log.info("uri=============>{}", uri);

        // 根据uri获取路径的权限
        SysMenu menu = menuService.getOne(QueryWrapper.create().where(SYS_MENU.URL.eq(StrUtil.replaceLast(uri, "/", ""))).and(SYS_MENU.METHOD.eq(request.getMethod())));
        if (menu == null) {
            return new AuthorizationDecision(false);
        }

        // 获取路径访问权限
        String perm = menu.getCode();
        log.info("路径权限=============>{}", perm);
        if (perm == null || perm.trim().isEmpty()) {
            return new AuthorizationDecision(true);
        }

        // 与用户权限集合做判断
        List<String> authorities = authentication.get().getAuthorities().stream().map(GrantedAuthority::getAuthority).distinct().collect(Collectors.toList());
        log.info("用户权限=============>{}", authorities);
        if (authorities.contains(perm)) {
            return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }
}
