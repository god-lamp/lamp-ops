package com.lamp.devops.controller;

import com.lamp.devops.entity.SysAccount;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.model.dto.PasswordDto;
import com.lamp.devops.model.dto.SysAccountDto;
import com.lamp.devops.service.ISysAccountService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  控制层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@RestController
@RequestMapping("/account")
public class SysAccountController {

    @Resource
    private ISysAccountService accountService;

    @GetMapping()
    @Parameters({
            @Parameter(name = "page", description = "页码"),
            @Parameter(name = "size", description = "每页大小"),
            @Parameter(name = "condition", description = "查询条件")
    })
    public IPage<SysAccount> findAllAccounts(@RequestParam(defaultValue = "1", required = false) Integer page,
                                             @RequestParam(defaultValue = "20", required = false) Integer size,
                                             @RequestParam(defaultValue = "", required = false) String condition) {
        return accountService.findAllAccounts(page, size, condition);
    }

    @PostMapping()
    public long addAccount(SysAccountDto account) {
        return accountService.addAccount(account);
    }

    @PutMapping("/{userId}")
    public boolean updateAccount(@PathVariable Long userId, SysAccountDto account) {
        return accountService.updateAccount(userId, account);
    }

    @DeleteMapping()
    public boolean delAccount(List<Long> userIds) {
        return accountService.delAccount(userIds);
    }

    @PostMapping("/{userId}/modify")
    public boolean modifyPassword(@PathVariable Long userId, @RequestBody PasswordDto password) {
        return accountService.modifyPassword(userId, password);
    }
}
