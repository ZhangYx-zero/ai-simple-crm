package com.aicrm.init;

import com.aicrm.entity.SysUser;
import com.aicrm.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 启动时初始化演示用户（幂等：已存在则跳过）。
 *
 * 为什么不在 schema.sql 里直接 INSERT？
 * 因为密码要存 BCrypt 密文，而 BCrypt 每次盐值随机，不能在 SQL 里写死——
 * 用 Java 在运行时 passwordEncoder.encode("123456") 生成最省事。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    /** 三个演示角色，覆盖数据权限三种视角 */
    private static final String PASSWORD = "123456";

    private final SysUserMapper sysUserMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        insertIfAbsent("admin", "系统管理员", "ADMIN");
        insertIfAbsent("manager", "销售主管老李", "MANAGER");
        insertIfAbsent("seller", "销售小王", "SALES");
    }

    private void insertIfAbsent(String username, String nickname, String role) {
        Long count = sysUserMapper.selectCount(
                Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (count != null && count > 0) {
            return;
        }
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setNickname(nickname);
        user.setRole(role);
        user.setStatus(1);
        sysUserMapper.insert(user);
        log.info("初始化演示用户：{} / {} / 角色 {} / 密码 {}", username, nickname, role, PASSWORD);
    }
}
