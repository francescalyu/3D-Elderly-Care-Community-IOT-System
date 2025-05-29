package com.francesca.config;


import com.francesca.mqtt.MqttPushClient;
import com.francesca.mqtt.MqttService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Client configuration for inbound messages.
 *
 * @Author francesca

 * 2025-05-16
 */

@Component
public class Mqttbean {

    @Resource
    private MqttService mqttService;

    @Bean("mqttPushClient")
    public MqttPushClient getMqttPushClient() {
        MqttPushClient mqttPushClient = new MqttPushClient(mqttService);
        return mqttPushClient;
    }

}
