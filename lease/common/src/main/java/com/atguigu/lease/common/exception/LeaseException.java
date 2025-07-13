package com.atguigu.lease.common.exception;

import com.atguigu.lease.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @BelongsProject: lease
 * @BelongsPackage: com.atguigu.lease.common.exception
 * @Author: GuoXiaofeng
 * @CreateTime: 2025-07-14  00:00
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class LeaseException extends RuntimeException {

    private Integer code;

    public LeaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public LeaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

}
