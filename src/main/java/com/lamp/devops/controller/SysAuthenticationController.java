package com.lamp.devops.controller;

import com.lamp.devops.model.dto.LoginInfo;
import com.lamp.devops.service.ISysAuthenticationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@RestController
public class SysAuthenticationController {

    @Resource
    private ISysAuthenticationService authenticationService;

    @PostMapping("/login")
    public String login(@RequestBody LoginInfo info) {
        return authenticationService.login(info);
    }


    @GetMapping("/verify")
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.verify(request, response);
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        return authenticationService.logout(response);
    }
}