package com.francesca.service;

import com.francesca.model.VO.Device.Device;

import java.math.BigInteger;

public interface CacheService {

        void put(BigInteger id, Device value);
        Device get(BigInteger id);
        boolean contains(BigInteger id);

        Device getDeviceByTopic(String upTopic);



}
