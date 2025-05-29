package com.francesca.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.francesca.config.MqttConfig;
import com.francesca.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author francesca

 * 2025-05-16
 */

@Component
@Slf4j
public class MqttServiceImpl implements MqttService {

    @Autowired
    MqttConfig mqttConfig;

    @Autowired
    private MqttSender mqttSender;

    @Autowired
    private PointService pointService;

    @Override
    public void sendMessage() {
        String jsonStr = null;
        mqttSender.send(mqttConfig.getTopic()[0], jsonStr);

    }

    @Override
    public void messageArrived(String topic , String msg) {
//        try {
//            //pointService.handleMqttMsg(topic, msg);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
         log.info("ok");
    }

    @Override
    public void messageHeartbeat() {
        log.info("监听心跳信息：{}", 1);
        //dataService.heartbeat(mqttResponseHeartbeat);
    }

    @Override
    public void subcribe() {

    }
}
