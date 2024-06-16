package com.edu.rent.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${minio.host}")
    private String minioHost;

    @Value("${minio.user}")
    private String user;

    @Value("${minio.password}")
    private String password;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
            .endpoint(minioHost)
            .credentials(user, password)
            .build();
    }
}
