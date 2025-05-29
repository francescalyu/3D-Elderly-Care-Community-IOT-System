package com.francesca.mqtt;

/**
 * @Author francesca

 * 2025-05-16
 */

public interface MqttService {


    void sendMessage();


    void messageArrived(String topic, String msg);


    void messageHeartbeat();

    void subcribe();
}
