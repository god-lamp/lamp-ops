package com.lamp.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.lamp.devops.common.SelectCommon;
import com.lamp.devops.entity.SysAccount;
import com.lamp.devops.entity.SysAccountRole;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.mapper.SysAccountMapper;
import com.lamp.devops.model.dto.PasswordDto;
import com.lamp.devops.model.dto.SysAccountDto;
import com.lamp.devops.service.ISysAccountRoleService;
import com.lamp.devops.service.ISysAccountService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.lamp.devops.entity.table.SysAccountRoleTableDef.SYS_ACCOUNT_ROLE;
import static com.lamp.devops.entity.table.SysAccountTableDef.SYS_ACCOUNT;
import static com.lamp.devops.entity.table.SysRoleTableDef.SYS_ROLE;

/**
 *  服务层实现。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {
    @Resource
    private ISysAccountRoleService accountRoleService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Value("${devops.default.passwd}")
    private String defaultPasswd;

    @Override
    public IPage<SysAccount> findAllAccounts(Integer page, Integer limit, String condition) {
        return new IPage<>(new SelectCommon<SysAccount>().findAll(page, limit, condition, this));
    }

    @Override
    public SysAccount findAccountByUsername(String username) {
        return this.getOne(QueryWrapper.create().where(SYS_ACCOUNT.USERNAME.eq(username)));
    }

    @Override
    public SysAccount findAccountByUserId(long userId) {
        return this.getById(userId);
    }

    @Override
    public long addAccount(SysAccountDto accountDto) {
        SysAccount account = BeanUtil.toBean(accountDto, SysAccount.class);
        account.setPassword(passwordEncoder.encode(defaultPasswd));
        if (!this.save(account)) {
            throw new IllegalStateException("添加用户失败");
        }

        List<SysAccountRole> accountRoles = accountDto.getRoleIds().stream().map(roleId -> SysAccountRole.builder().accountId(account.getId()).roleId(roleId).build()).collect(Collectors.toList());
        if (!accountRoleService.saveBatch(accountRoles)) {
            throw new IllegalStateException("添加用户信息失败");
        }
        return account.getId();
    }

    @Override
    public boolean updateAccount(long userId, SysAccountDto accountDto) {
        SysAccount account = BeanUtil.toBean(accountDto, SysAccount.class, CopyOptions.create().ignoreNullValue());
        account.setId(userId);
        if (!this.updateById(account)) {
            throw new IllegalStateException("修改用户失败");
        }

        // 修改用户和角色的关联关系
        if (CollUtil.isNotEmpty(accountDto.getRoleIds())) {
            if (!accountRoleService.remove(QueryWrapper.create().where(SYS_ACCOUNT_ROLE.ACCOUNT_ID.eq(userId)))) {
                throw new IllegalStateException("修改用户信息失败");
            }
            List<SysAccountRole> accountRoles = accountDto.getRoleIds().stream().map(roleId -> SysAccountRole.builder().accountId(account.getId()).roleId(roleId).build()).collect(Collectors.toList());
            if (!accountRoleService.saveBatch(accountRoles)) {
                throw new IllegalStateException("修改用户信息失败");
            }
        }
        return true;
    }

    @Override
    public boolean delAccount(List<Long> userIds) {
        List<SysAccount> accounts = this.list(
                QueryWrapper.create()
                        .select(SYS_ACCOUNT.USERNAME)
                        .from(SYS_ACCOUNT)
                        .leftJoin(SYS_ACCOUNT_ROLE).on(SYS_ACCOUNT_ROLE.ACCOUNT_ID.eq(SYS_ACCOUNT.ID))
                        .leftJoin(SYS_ROLE).on(SYS_ACCOUNT_ROLE.ROLE_ID.eq(SYS_ROLE.ID))
                        .where(SYS_ACCOUNT.ID.in(userIds).and(SYS_ROLE.CODE.eq("admin")))
        );
        if (CollUtil.isNotEmpty(accounts)) {
            throw new IllegalStateException(String.format("用户【%s】是管理员，不允许删除", CollUtil.join(accounts.stream().map(SysAccount::getUsername).distinct().toList(), ",")));
        }
        if (!this.removeByIds(userIds)) {
            throw new IllegalStateException("删除用户失败");
        }
        return accountRoleService.remove(QueryWrapper.create().where(SYS_ACCOUNT_ROLE.ACCOUNT_ID.in(userIds)));
    }

    @Override
    public boolean modifyPassword(Long userId, PasswordDto password) {
        SysAccount account = this.getById(userId);

        // 验证旧密码是否正确
        if (!passwordEncoder.matches(password.getOldPassword(), account.getPassword())) {
            throw new IllegalStateException("旧密码输入错误");
        }

        account.setPassword(passwordEncoder.encode(password.getNewPassword()));
        if (!this.updateById(account)) {
            throw new IllegalStateException("修改密码失败");
        }
        return false;
    }
}
