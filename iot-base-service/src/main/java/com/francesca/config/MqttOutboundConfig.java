package com.francesca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class MqttOutboundConfig {

    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    @Resource
    private MqttConfig mqttConfig;

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        String clientId = mqttConfig.getClientId();
        clientId += System.currentTimeMillis();

        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                clientId,
                mqttClientFactory);
        handler.setAsync(true);
        handler.setDefaultQos(1);
        return handler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}
