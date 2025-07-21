package com.francesca.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.dash.DashDoorVO;
import com.francesca.model.VO.dash.DashSosVO;
import com.francesca.model.VO.dash.HealthVO;
import com.francesca.mqtt.bluetouth.*;
import com.francesca.mqtt.geekopen.GeekOpen16AOutlet;
import com.francesca.mqtt.ustoneMsg.*;
import com.francesca.service.CacheService;
import com.francesca.service.PointService;
import com.francesca.util.CommonUtil;
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

       //Geekopen 10A outlet
        if (device.getPid() == BigInteger.valueOf(1)) {
            ObjectMapper mapper = new ObjectMapper();

            GeekOpen16AOutlet geekOpen16AOutlet = mapper.readValue(msg,GeekOpen16AOutlet.class);
            log.info("============》》rcv geekOpen 10A Outle Msg , device id : " + device.getManuId() );
            cacheService.putGeekOpen16AOutlet(device.getId(), geekOpen16AOutlet);

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

        //ustone 3 way Switcher
        if (device.getPid() == BigInteger.valueOf(9)) {
            ObjectMapper mapper = new ObjectMapper();
            UStone3WaySwitchMsg switchData = mapper.readValue(msg, UStone3WaySwitchMsg.class);

            log.info("============》》rcv ustone 3 way switch Msg , device id : " + device.getManuId() );
            UStone3WaySwitch uStone3WaySwitch = switchData.getStatus();
            cacheService.putUStone3WaySwitch(device.getId(), uStone3WaySwitch);

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
                        HealthVO health = cacheService.getHealth();

                        if(ObjectUtil.isNotEmpty(healthBandEvent) ){

                            // if heart rate = 255, 手环测量中，手环每5分钟测量一次
                            if(ObjectUtil.isEmpty(health.getHeart_rate())){
                                health.setHeart_rate("测量中");
                            }else {
                                int heart = Integer.parseInt(healthBandEvent.getHeart_rate());
                                if ( heart > 0 && heart != 255  ){
                                    health.setHeart_rate(healthBandEvent.getHeart_rate());
                                }
                            }

                            if(ObjectUtil.isNotEmpty(healthBandEvent.getBlood_pressure())){
                                int blood = Integer.parseInt(healthBandEvent.getBlood_pressure());

                                if (blood > 0 && blood < 255){
                                    health.setBlood_pressure(healthBandEvent.getBlood_pressure());
                                }
                            }

                            if (ObjectUtil.isNotEmpty(healthBandEvent.getFalling_alert())){
                                health.setFalling_alert(healthBandEvent.getFalling_alert());
                            }

                            if(ObjectUtil.isNotEmpty(healthBandEvent.getBody_temperature())){
                                health.setBody_temperature(healthBandEvent.getBody_temperature());
                            }

                            if(ObjectUtil.isNotEmpty(healthBandEvent.getNot_wearing_alert())){
                                health.setNot_wearing_alert(healthBandEvent.getNot_wearing_alert());
                            }

                            if(ObjectUtil.isNotEmpty(healthBandEvent.getTotal_calories())){
                                health.setTotal_calories(healthBandEvent.getTotal_calories());
                            }

                            if(ObjectUtil.isNotEmpty(healthBandEvent.getTotal_sleep())){
                                health.setTotal_sleep(healthBandEvent.getTotal_sleep());
                            }

                            if(ObjectUtil.isNotEmpty(healthBandEvent.getTotal_steps())){
                                health.setTotal_steps(healthBandEvent.getTotal_steps());
                            }

                            if(ObjectUtil.isNotEmpty(healthBandEvent.getBattery_percentage())){
                                health.setBattery_percentage(healthBandEvent.getBattery_percentage());
                            }

                            cacheService.setHealth(health);
                            cacheService.putUStoneHealthBand(BigInteger.valueOf(10),healthBandEvent);
                        }


                        //log.info("========== rcv bluetooth healthBand msg  mac : " + msg);
                        break;
                    case "button":
                        ButtonEvent buttonEvent = (ButtonEvent) event;
                        DashSosVO dashSosVO = cacheService.getSos();

                        if(ObjectUtil.isNotEmpty(buttonEvent)){

                             if(buttonEvent.getButton2() == "1"){

                                 //如果呼救器按下计数 > 0
                                 if(ObjectUtil.isNotEmpty(dashSosVO.getCount())  && Integer.parseInt(dashSosVO.getCount()) > 0 ){

                                    int min = Math.toIntExact(DateUtil.between(DateUtil.parseDate(dashSosVO.getPressTime()), DateUtil.date(), DateUnit.MINUTE));

                                    //按下呼救 时间间隔大于5分钟，上次为误按
                                    if (min > 5){
                                        dashSosVO.setPressTime(DateUtil.now());
                                        dashSosVO.setCount("1");

                                    }else {
                                        int count = Integer.parseInt(dashSosVO.getCount());

                                        if (count >= 5){

                                            dashSosVO.setStatus("1");
                                            dashSosVO.setSosTime(DateUtil.now());
                                        }

                                        count = count + 1;
                                        dashSosVO.setPressTime(DateUtil.now());
                                        dashSosVO.setCount(String.valueOf(count));
                                    }
                                 }

                             }

                        }

                        cacheService.setSos(dashSosVO);
                        cacheService.putUStoneBlueToothButton(BigInteger.valueOf(18),buttonEvent);

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
                            doorSensorEvent.setChangeDate(DateUtil.now());
                        }

                        cacheService.setDashDoor(dashDoorVO);
                        cacheService.putUStoneDoorSensor(BigInteger.valueOf(11),doorSensorEvent);

                        //log.info("========== rcv unKnow bluetooth msg : " + doorSensorEvent.getMac());
                        break;
                    case "multiButton":
                        MultiButtonEvent multiButtonEvent = (MultiButtonEvent) event;
                        //log.info("========== rcv unKnow bluetooth msg : " + multiButtonEvent.getMac());
                        break;
                    case "lightSensor":
                        LightSensorEvent lightSensorEvent = (LightSensorEvent) event;
                        DashDoorVO dashDoor = cacheService.getDashDoor();

                        if(ObjectUtil.isNotEmpty(lightSensorEvent)){

                            if(ObjectUtil.isNotEmpty(lightSensorEvent.getHumidity())){
                                dashDoor.setHumidity(CommonUtil.toNumberStr(lightSensorEvent.getHumidity()));
                            }

                            if(ObjectUtil.isNotEmpty(lightSensorEvent.getIlluminance())){
                                dashDoor.setIlluminance(CommonUtil.toNumberStr(lightSensorEvent.getIlluminance()));
                            }

                            if(ObjectUtil.isNotEmpty(lightSensorEvent.getTemperature())){
                                dashDoor.setTemperature(CommonUtil.toNumberStr(lightSensorEvent.getTemperature()));
                            }

                        }

                        cacheService.setDashDoor(dashDoor);
                        cacheService.putUStoneLightSensor(BigInteger.valueOf(19),lightSensorEvent);

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
