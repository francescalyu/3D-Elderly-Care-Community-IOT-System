package com.francesca.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.dash.DashDoorVO;
import com.francesca.mqtt.bluetouth.*;
import com.francesca.mqtt.ustoneMsg.*;
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
           UStone10AOutlet uStone10AOutlet = uStone10AOutletMsg.getStatus();
           cacheService.putUStone10AOutlet(device.getId(), uStone10AOutlet);



       }

       //ustone air 6 sensor
       if(device.getPid() == BigInteger.valueOf(3)){
           ObjectMapper objectMapper = new ObjectMapper();
           UStoneAirSixSensorData sensorData = objectMapper.readValue(msg, UStoneAirSixSensorData.class);
           log.info("============》》rcv ustone Air Six Sensor Msg , device id : " + device.getManuId() );

           cacheService.putUStoneAirSixSensorStatus(device.getId() , sensorData.getStatus());
           cacheService.updateCurrentAir(sensorData.getStatus());
       }

        //ustone smoke sensor
        if(device.getPid() == BigInteger.valueOf(4)){
            ObjectMapper objectMapper = new ObjectMapper();
            UStoneSmokeSensorData sensorData = objectMapper.readValue(msg, UStoneSmokeSensorData.class);
            log.info("============》》rcv ustone Smoke Sesor Msg , device id : " + device.getManuId() );
            cacheService.putUStoneSmokeSensorStatus(device.getId(),sensorData.getStatus());
        }

        if (device.getPid() == BigInteger.valueOf(5)){
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(msg);
            EventWrapper eventWrapper = new EventWrapper();
            JsonNode eventNode = jsonNode.get("event");

            if(ObjectUtil.isEmpty(eventNode)){
                return;
            }

            BaseEvent event = null;
            try {
                event = determineEventType(eventNode, objectMapper);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (ObjectUtil.isNotEmpty(event)){

                eventWrapper.setEvent(event);

                switch (event.getEventType()) {
                    case "healthBand":
                        HealthBandEvent healthBandEvent = (HealthBandEvent) event;
                        log.info("========== rcv bluetooth healthBand msg  mac : " + msg);
                        break;
                    case "button":
                        ButtonEvent buttonEvent = (ButtonEvent) event;
                        //log.info("========== rcv unKnow bluetooth msg : " + buttonEvent.getMac());
                        break;
                    case "doorSensor":
                        DoorSensorEvent doorSensorEvent = (DoorSensorEvent) event;
                        DashDoorVO dashDoorVO = cacheService.getDashDoor();

                        if(ObjectUtil.isEmpty(dashDoorVO.getStatus())){
                            dashDoorVO.setStatus(doorSensorEvent.getWindoor_open());
                            dashDoorVO.setChangeDate(DateUtil.now());
                        }

                        if (dashDoorVO.getStatus() != doorSensorEvent.getWindoor_open()){
                            dashDoorVO.setStatus(doorSensorEvent.getWindoor_open());
                            dashDoorVO.setChangeDate(DateUtil.now());
                        }

                        //log.info("========== rcv unKnow bluetooth msg : " + doorSensorEvent.getMac());
                        break;
                    case "multiButton":
                        MultiButtonEvent multiButtonEvent = (MultiButtonEvent) event;
                        //log.info("========== rcv unKnow bluetooth msg : " + multiButtonEvent.getMac());
                        break;
                    case "lightSensor":
                        LightSensorEvent lightSensorEvent = (LightSensorEvent) event;
                        //log.info("========== rcv unKnow bluetooth msg : " + device.getManuId());
                        break;
                    default:
                        //log.info("========== rcv unKnow bluetooth msg : " + device.getManuId());

                }
            }


        }


    }


    private static BaseEvent determineEventType(JsonNode eventNode , ObjectMapper objectMapper) throws Exception {
        if (eventNode.has("name")) {
            return objectMapper.treeToValue(eventNode, HealthBandEvent.class);
        } else if (eventNode.has("button1")) {
            return objectMapper.treeToValue(eventNode, MultiButtonEvent.class);
        } else if (eventNode.has("windoor_open")) {
            return objectMapper.treeToValue(eventNode, DoorSensorEvent.class);
        } else if (eventNode.has("button2")) {
            return objectMapper.treeToValue(eventNode, ButtonEvent.class);
        } else if (eventNode.has("illuminance")) {
            return objectMapper.treeToValue(eventNode, LightSensorEvent.class);
        }
        throw new IllegalArgumentException("Unknown event type");
    }

}
