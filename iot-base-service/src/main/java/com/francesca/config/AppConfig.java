package com.francesca.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author francesca

 * 2025-05-16
 */
@Configuration
@Data
public class AppConfig {

    @Value("${app.id}")
    private String appId;

    @Value("${mqtt.upload.server}")
    private String mqttServer;

}
