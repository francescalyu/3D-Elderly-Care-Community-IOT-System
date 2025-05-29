package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.francesca.dao.DeviceDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.VO.Device.Device;
import com.francesca.service.CacheService;
import com.francesca.service.DeviceMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryCacheService implements CacheService {

    private final Map<BigInteger, Device> cache = new ConcurrentHashMap<>();

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private DeviceMsg deviceMsg;

    @PostConstruct
    public void initCache(){
        List<DeviceEntity> deviceEntityList = deviceDao.selectAll();
        if (ObjectUtil.isEmpty(deviceEntityList)){
            return;
        }
        deviceEntityList.stream().forEach(
                v-> {
                     cache.put(v.getId(), deviceMsg.setDevice(v) );
                }
        );

    }


    @Override
    public void put(BigInteger id, Device value) {
        cache.put(id , value);
    }

    @Override
    public Device get(BigInteger id) {
        return cache.get(id);
    }

    @Override
    public boolean contains(BigInteger id) {
        return cache.containsKey(id);
    }

    @Override
    public Device getDeviceByTopic(String upTopic) {

        return this.cache.values().stream()
                .filter(p -> p.getTopicUp() != null && upTopic.trim().equals(p.getTopicUp().trim()))
                .findFirst()
                .orElse(null);
    }
}