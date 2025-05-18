package com.francesca.config;


import com.francesca.mqtt.MqttPushClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Client configuration for inbound messages.
 *
 * @Author francesca

 * 2025-05-16
 */

@Component
public class Mqttbean {

    @Bean("mqttPushClient")
    public MqttPushClient getMqttPushClient() {
        MqttPushClient mqttPushClient = new MqttPushClient();
        return mqttPushClient;
    }

}
