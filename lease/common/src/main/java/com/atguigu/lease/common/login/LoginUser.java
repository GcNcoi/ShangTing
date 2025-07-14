package com.atguigu.lease.common.login;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @BelongsProject: lease
 * @BelongsPackage: com.atguigu.lease.common.login
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-15  00:01
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class LoginUser {

    private Long userId;

    private String username;
}