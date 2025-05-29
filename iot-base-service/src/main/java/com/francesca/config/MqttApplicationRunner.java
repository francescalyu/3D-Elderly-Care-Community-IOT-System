package com.francesca.config;


import com.francesca.mqtt.MqttPushClient;
import com.francesca.mqtt.MqttService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author francesca

 * 2025-05-16
 */

@Component
@Slf4j
public class MqttApplicationRunner implements ApplicationRunner {

    @Autowired
    private MqttConfig mqttConfig;

    @Autowired
    private MqttService mqttService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("===============>>>Mqtt is run starting:<<==================");
        }
        MqttPushClient mqttPushClient = new MqttPushClient(mqttService);
        mqttPushClient.connect(mqttConfig);
        // 订阅主题
        mqttPushClient.subscribe(mqttConfig.getTopic(), mqttConfig.getQos());
    }

}