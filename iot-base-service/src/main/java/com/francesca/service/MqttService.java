package com.francesca.service;


import com.francesca.mqtt.MqttResponseBody;

/**
 * 点位上报器
 *
 /**
 * @Author francesca

 * 2025-05-16
 */

public interface MqttService {


    void sendMessage();

    void messageArrived(MqttResponseBody mqttResponseBody);

    void messageHeartbeat();

    /**
     * 上传点位到远端
     */
    void subcribe();

}
