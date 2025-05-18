package com.francesca.mqtt;

/**
 * @Author francesca

 * 2025-05-16
 */

public interface MqttService {


    void sendMessage();


    void messageArrived(MqttResponseBody mqttResponseBody);


    void messageHeartbeat();

    void subcribe();
}
