package com.atguigu.lease.common.minioconfig;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述
 *
 * @author: Gxf
 * @date: 2025年07月10日 16:30
 */
@Configuration
//@EnableConfigurationProperties(MinioProperties.class)
@ConfigurationPropertiesScan("com.atguigu.lease.common.minioconfig")
public class MinioConfiguration {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

}
