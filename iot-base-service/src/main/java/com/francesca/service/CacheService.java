package com.francesca.service;

import com.francesca.model.DTO.*;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.dash.*;
import com.francesca.mqtt.bluetouth.ButtonEvent;
import com.francesca.mqtt.bluetouth.DoorSensorEvent;
import com.francesca.mqtt.bluetouth.HealthBandEvent;
import com.francesca.mqtt.bluetouth.LightSensorEvent;
import com.francesca.mqtt.geekopen.GeekOpen16AOutlet;
import com.francesca.mqtt.geekopen.GeekOpenACController;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStone3WaySwitch;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;
import com.francesca.mqtt.video.HkVideo;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内部缓存
 *
 /**
 * @Author francesca

 * 2025-05-16
 */

public interface CacheService {

        void put(BigInteger id, Device value);
        Device get(BigInteger id);
        boolean contains(BigInteger id);

        Device getDeviceByTopic(String upTopic);

        DeviceEntity getDevice (BigInteger id);

        List<Device> getAllDevice();

        ProductEntity getProduct (BigInteger id);

        PointEntity getPoint(BigInteger id);

        void putUStone10AOutlet(BigInteger id , UStone10AOutlet uStone10AOutlet);

        void putGeekOpen16AOutlet(BigInteger id , GeekOpen16AOutlet geekOpen16AOutlet);

        void putUStone3WaySwitch(BigInteger id , UStone3WaySwitch uStone3WaySwitch);

        ConcurrentHashMap<BigInteger , UStone3WaySwitch > getUStone3WaySwitch();

        ConcurrentHashMap<BigInteger , GeekOpen16AOutlet > getGeekOpen16AOutlet();

        List<UStone10AOutlet> getAllUStone10AOutletStatus();

        ConcurrentHashMap<BigInteger , UStone10AOutlet > getUStone10AOutlets();

        void putUStoneAirSixSensorStatus(BigInteger id , UStoneAirSixSensorStatus uStoneAirSixSensorStatus);

        ConcurrentHashMap<BigInteger , UStoneAirSixSensorStatus > getUstoneAirSixSensors();

        void putUStoneSmokeSensorStatus(BigInteger id , UStoneSmokeSensorStatus uStoneSmokeSensorStatus);

        ConcurrentHashMap<BigInteger , UStoneSmokeSensorStatus > getUstoneSmokeSensors();


        void updateCurrentAir(UStoneAirSixSensorStatus uStoneAirSixSensorStatus);

        void updateSmoke(DashAirVO dashAirVO);

        DashAirVO getCurrentAir();

        DashPowerVO getCurrentPower();

        DashPowerVO get5minPower();

        void set5minPower(DashPowerVO dashPowerVO);

        DashPowerVO get1hPower();

        DashPowerVO setdayPower();

        void set1hPower(DashPowerVO dashPowerVO);

        void putDashPower(DashPowerVO dashPowerVO);

        List<WarnRuleEntity> readWarnRule(BigInteger prodId,   int closeOpen);

        WarnEntity readWarn(BigInteger warn);

        DashDoorVO getDashDoor();

        void setDashDoor(DashDoorVO dashDoorVO);

        HealthVO getHealth();

        HealthVO getHealth1h();

        void setHealth1h(HealthVO healthVO);

        void setHealth(HealthVO healthVO);

        DashSosVO getSos();

        void setSos(DashSosVO dashSosVO);

        void putUStoneHealthBand(BigInteger devid , HealthBandEvent healthBandEvent);

        ConcurrentHashMap<BigInteger,HealthBandEvent> getUStoneHealthBand();

        void putUStoneDoorSensor(BigInteger devid, DoorSensorEvent doorSensorEvent);

        ConcurrentHashMap<BigInteger,DoorSensorEvent> getUStoneDoorSensor();

        void putUStoneLightSensor(BigInteger devid, LightSensorEvent lightSensorEvent);

        ConcurrentHashMap<BigInteger,LightSensorEvent> getUStoneLightSensor();

        void putUStoneBlueToothButton(BigInteger devid, ButtonEvent buttonEvent);

        ConcurrentHashMap<BigInteger,ButtonEvent> getUStoneBlueToothButton();

        ConcurrentHashMap<Integer, GeekOpenACController> getGeekOpenACController();

        List<TkruleEntity> getTkRules();

        HkVideo getHkVideo();

}
