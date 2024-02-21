package com.lamp.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.lamp.devops.entity.SysAccount;
import com.lamp.devops.entity.SysMenu;
import com.lamp.devops.entity.SysRole;
import com.lamp.devops.exception.IAuthenticationException;
import com.lamp.devops.fastmap.IFastMap;
import com.lamp.devops.model.dto.LoginInfo;
import com.lamp.devops.model.dto.LoginResult;
import com.lamp.devops.model.dto.Personal;
import com.lamp.devops.service.ISysAccountService;
import com.lamp.devops.service.ISysAuthenticationService;
import com.lamp.devops.service.ISysMenuService;
import com.lamp.devops.service.ISysRoleService;
import com.lamp.devops.utils.RandomValidateCodeUtil;
import com.lamp.devops.utils.TokenProviderUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.header.CacheControlServerHttpHeadersWriter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author god-lamp
 * @since 2024-02-20
 */
@Slf4j
@Service
public class SysAuthenticationServiceImpl implements ISysAuthenticationService {
    @Resource
    private IFastMap<String, String> fastMap;

    @Resource
    private ISysAccountService accountService;
    @Resource
    private ISysMenuService menuService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private ISysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        SysAccount account = accountService.findAccountByUsername(username);
        Assert.notNull(account, String.format("指定的用户【%s】不存在", username));
        List<SysMenu> menus = menuService.findMenusByAccountId(account.getId());
        log.info("menus: {}", menus);
        List<SimpleGrantedAuthority> authorities = menus.stream().map(menu -> new SimpleGrantedAuthority(menu.getCode())).collect(Collectors.toList());
        log.info("authorities: {}", authorities);
        account.setAuthorities(authorities);
        log.info("user details: {}", account);
        return account;
    }

    @Override
    public Map<String, String> login(LoginInfo info) {
        SysAccount account = accountService.findAccountByUsername(info.getUsername());
        String password = info.getPassword();

        log.info("密码对比结果:{}", passwordEncoder.matches(password, account.getPassword()));
        try {
            if (!passwordEncoder.matches(password, account.getPassword())) {
                throw new IAuthenticationException("用户密码不正确!");
            }
            if (!account.isEnabled() || !account.isAccountNonLocked()) {
                throw new IllegalStateException("账户不可用或已被锁!");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("user 【{}】 logged into the system at the {}.", info.getUsername(), LocalDateTime.now());
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }
        return Map.of("token", TokenProviderUtil.token(info.getUsername()));
    }

    @Override
    public String logout(HttpServletResponse response) {
        try {
            SecurityContextHolder.clearContext();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.flushBuffer();
        } catch (IOException ex) {
            log.info("推出认证失败，原因：{}", ex.getMessage());
            throw new IllegalStateException("请求失败");
        }
        return "success";
    }

    @Override
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        // 验证码的过期时间
        long expiredTime = 3 * 60 * 1000;
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", CacheControlServerHttpHeadersWriter.PRAGMA_VALUE);
        response.setDateHeader("Expires", System.currentTimeMillis() + expiredTime);
        response.setHeader("Access-Control-Expose-Headers", "key");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        response.setHeader("Key", uuid);
        try {
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            String randomCode = randomValidateCode.getRandomCode(request, response);
            fastMap.put(uuid, randomCode);
            // 设置过期的回调事件
            fastMap.expire(uuid, expiredTime, (key, val) -> fastMap.remove(uuid));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public LoginResult info(Principal principal) {
        String username = principal.getName();
        SysAccount account = accountService.findAccountByUsername(username);
        LoginResult result = BeanUtil.copyProperties(account, LoginResult.class);
        result.setRoles(roleService.findRolesByAccountId(account.getId()));
        return result;
    }

    @Override
    public Personal personalInfo(Principal principal) {
        String username = principal.getName();
        SysAccount account = accountService.findAccountByUsername(username);
        Set<SysRole> roles = roleService.findRoleByUsername(username);
        account.setRoles(roles.stream().map(SysRole::getCode).collect(Collectors.toSet()));
        return BeanUtil.toBean(account, Personal.class);
    }
}
