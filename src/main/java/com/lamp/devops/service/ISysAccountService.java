package com.lamp.devops.service;

import com.lamp.devops.entity.SysAccount;
import com.lamp.devops.lang.IPage;
import com.lamp.devops.model.dto.PasswordDto;
import com.lamp.devops.model.dto.SysAccountDto;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 服务层。
 *
 * @author god-lamp
 * @since 2024-02-20
 */
public interface ISysAccountService extends IService<SysAccount> {
    /**
     * 查询所有账户信息
     *
     * @param page      页码
     * @param size      每页大小
     * @param condition 查询条件
     * @return 分页对象
     */
    IPage<SysAccount> findAllAccounts(Integer page, Integer size, String condition);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 账户信息
     */
    SysAccount findAccountByUsername(String username);

    /**
     * 根据用户ID查询账户信息
     *
     * @param userId 账户ID
     * @return 用户信息
     */
    SysAccount findAccountByUserId(long userId);

    /**
     * 添加账户
     *
     * @param account 账户信息
     * @return 账户主键ID
     */
    long addAccount(SysAccountDto account);

    /**
     * 更新账户信息
     *
     * @param userId  用户ID
     * @param account 账户信息
     * @return true|false
     */
    boolean updateAccount(long userId, SysAccountDto account);

    /**
     * 批量删除账户信息
     *
     * @param userIds 用户ID列表
     * @return true|false
     */
    boolean delAccount(List<Long> userIds);

    /**
     * 修改用户密码
     *
     * @param userId   用户ID
     * @param password 秘钥对象
     * @return true|false
     */
    boolean modifyPassword(Long userId, PasswordDto password);
}
