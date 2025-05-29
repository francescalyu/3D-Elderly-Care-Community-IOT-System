package com.francesca.service;


import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 点位上报器
 *
 /**
 * @Author francesca

 * 2025-05-16
 */

public interface PointService {

    void handleMqttMsg(String topic , String msg) throws JsonProcessingException;

}
