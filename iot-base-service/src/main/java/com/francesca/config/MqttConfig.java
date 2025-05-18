package com.francesca.config;


import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author francesca

 * 2025-05-16
 */
@Data
@Component
@Configuration
public class MqttConfig {

    /**
     * 链接url
     */
    @Value("${mqtt.url}")
    private String url;

    /**
     * 用户名
     */
    @Value("${mqtt.username}")
    private String username;

    /**
     * 密码
     */
    @Value("${mqtt.password}")
    private String password;

    /**
     * 客户端id
     */
    @Value("${mqtt.client-id}")
    private String clientId;

    /**
     * 通讯标识 id
     */
    @Value("${mqtt.id}")
    private String id;

    /**
     * 主题
     */
    @Value("${mqtt.topics}")
    private String[] topic;

    /**
     * 超时时间
     */
    @Value("${mqtt.timeout}")
    private int timeout;

    /**
     * 心跳检测时间
     */
    @Value("${mqtt.keep-alive}")
    private int keepAlive;

    /**
     * 心跳包级别
     */
    @Value("${mqtt.qos}")
    private int[] qos;

    private int completionTimeout;

}

