package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.francesca.model.VO.Device.Device;
import com.francesca.mqtt.ustoneMsg.UStone10AOutletModule;
import com.francesca.mqtt.ustoneMsg.UStone10AOutletMsg;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorData;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorData;
import com.francesca.service.CacheService;
import com.francesca.service.PointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Slf4j
public class PointServiceImpl implements PointService {

    @Autowired
    private CacheService cacheService;




    @Override
    public void handleMqttMsg(String topic, String msg) throws JsonProcessingException {

       Device device =  cacheService.getDeviceByTopic(topic);
       if (ObjectUtil.isEmpty(device)){
           log.info("============》》No Device find with this topic : " + topic  + " === msg :" + msg );
           return;
       }

       //ustone 10A outlet
       if (device.getPid() == BigInteger.valueOf(2)) {
           ObjectMapper mapper = new ObjectMapper();
           mapper.registerModule(new JavaTimeModule());
           mapper.registerModule(new UStone10AOutletModule());

           UStone10AOutletMsg uStone10AOutletMsg = mapper.readValue(msg,UStone10AOutletMsg.class);
           log.info("============》》rcv ustone 10A Outle Msg , device id : " + device.getManuId() );
           cacheService.putUStone10AOutlet(device.getId(), uStone10AOutletMsg.getStatus());
       }

       //ustone air 6 sensor
       if(device.getPid() == BigInteger.valueOf(3)){
           ObjectMapper objectMapper = new ObjectMapper();
           UStoneAirSixSensorData sensorData = objectMapper.readValue(msg, UStoneAirSixSensorData.class);
           log.info("============》》rcv ustone Air Six Sensor Msg , device id : " + device.getManuId() );
           cacheService.putUStoneAirSixSensorStatus(device.getId() , sensorData.getStatus());
       }

        //ustone smoke sensor
        if(device.getPid() == BigInteger.valueOf(4)){
            ObjectMapper objectMapper = new ObjectMapper();
            UStoneSmokeSensorData sensorData = objectMapper.readValue(msg, UStoneSmokeSensorData.class);
            log.info("============》》rcv ustone Smoke Sesor Msg , device id : " + device.getManuId() );
            cacheService.putUStoneSmokeSensorStatus(device.getId(),sensorData.getStatus());
        }

    }
}
