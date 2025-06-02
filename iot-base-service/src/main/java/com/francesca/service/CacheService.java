package com.francesca.service;

import com.francesca.model.VO.Device.Device;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;

import java.math.BigInteger;

public interface CacheService {

        void put(BigInteger id, Device value);
        Device get(BigInteger id);
        boolean contains(BigInteger id);

        Device getDeviceByTopic(String upTopic);

        void putUStone10AOutlet(BigInteger id , UStone10AOutlet uStone10AOutlet);

        void putUStoneAirSixSensorStatus(BigInteger id , UStoneAirSixSensorStatus uStoneAirSixSensorStatus);

        void putUStoneSmokeSensorStatus(BigInteger id , UStoneSmokeSensorStatus uStoneSmokeSensorStatus);

        void updateCurrentAir(UStoneAirSixSensorStatus uStoneAirSixSensorStatus);

}
