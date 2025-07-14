package com.atguigu.lease.common.login;

/**
 * @BelongsProject: lease
 * @BelongsPackage: com.atguigu.lease.common.login
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-15  00:01
 * @Description: TODO
 * @Version: 1.0
 */
public class LoginUserHolder {

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    public static void setLoginUser(LoginUser loginUser) {
        threadLocal.set(loginUser);
    }

    public static LoginUser getLoginUser() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }

}
