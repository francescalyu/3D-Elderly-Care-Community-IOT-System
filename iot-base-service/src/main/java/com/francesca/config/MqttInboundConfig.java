package com.francesca.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.francesca.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

@Configuration
@Slf4j
public class MqttInboundConfig {

    @Autowired
    private MqttPahoClientFactory mqttClientFactory;

    @Resource
    private MqttConfig mqttConfig;

    @Autowired
    private PointService pointService;

    @Bean
    public MessageProducer inbound() {
        String clientId = mqttConfig.getClientId();
        clientId += System.currentTimeMillis();

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId,
                        mqttClientFactory,
                        mqttConfig.getTopic());
        adapter.setCompletionTimeout(5000);
        adapter.setQos(mqttConfig.getQos());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler messageHandler() {
        return message -> {
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            String payload = message.getPayload().toString();

//            log.info("============》》接收消息主题 : " + topic);
//            log.info("============》》接收消息内容原始内容 : " + new String(payload));

            try {
                pointService.handleMqttMsg(topic , payload);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        };
    }
}
