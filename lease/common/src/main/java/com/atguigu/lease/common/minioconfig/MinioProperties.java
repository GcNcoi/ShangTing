package com.atguigu.lease.common.minioconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 功能描述: minIO属性配置
 *
 * @author: Gxf
 * @date: 2025年07月10日 16:35
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;

}
