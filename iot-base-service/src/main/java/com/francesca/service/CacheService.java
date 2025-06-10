package com.francesca.service;

import com.francesca.model.DTO.*;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.dash.DashAirVO;
import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

        void putDashPower(DashPowerVO dashPowerVO);

        List<WarnRuleEntity> readWarnRule(BigInteger prodId,   int closeOpen);

        WarnEntity readWarn(BigInteger warn);



}
