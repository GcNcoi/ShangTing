package com.atguigu.lease.common.constant;

/**
 * @BelongsProject: lease
 * @BelongsPackage: com.atguigu.lease.common.constant
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-14  22:40
 * @Description: TODO 常量命名
 * @Version: 1.0
 */
public class RedisConstant {
    public static final String ADMIN_LOGIN_PREFIX = "admin:login:";

    public static final Integer ADMIN_LOGIN_CAPTCHA_TTL_SEC = 60;

    public static final String APP_LOGIN_PREFIX = "app:login:";

    public static final Integer APP_LOGIN_CODE_RESEND_TIME_SEC = 60;

    public static final Integer APP_LOGIN_CODE_TTL_SEC = 60 * 10;

    public static final String APP_ROOM_PREFIX = "app:room:";
}
