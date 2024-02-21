package com.lamp.devops.controller;

import com.lamp.devops.model.dto.LoginInfo;
import com.lamp.devops.model.dto.Personal;
import com.lamp.devops.service.ISysAuthenticationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 *  控制层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@RestController
public class SysAuthenticationController {

    @Resource
    private ISysAuthenticationService service;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginInfo info) {
        return service.login(info);
    }

    @GetMapping("/info")
    public Personal personalInfo(Principal principal) {
        return service.personalInfo(principal);
    }


    @GetMapping("/verify")
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        service.verify(request, response);
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        return service.logout(response);
    }
}