package com.francesca.mqtt;

import com.francesca.config.MqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author francesca

 * 2025-05-16
 */

@Service
@Slf4j
public class MqttServiceImpl implements MqttService {

    @Autowired
    MqttConfig mqttConfig;

    @Autowired
    private MqttSender mqttSender;

//    @Autowired
//    DataService dataService;

    @Override
    public void sendMessage() {
        String jsonStr = null;
        mqttSender.send(mqttConfig.getTopic()[0], jsonStr);

    }

    @Override
    public void messageArrived(MqttResponseBody mqttResponseBody) {
        log.info("接口的消息：{}", mqttResponseBody );
        //dataService.save(mqttResponseBody);
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
