package com.francesca.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.francesca.dao.DeviceDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.dash.DashAirVO;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;
import com.francesca.service.CacheService;
import com.francesca.service.DeviceMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryCacheService implements CacheService {

    private final Map<BigInteger, Device> cache = new ConcurrentHashMap<>();

    private final Map<BigInteger, UStone10AOutlet> uStone10AOutletLast = new ConcurrentHashMap<>();

    private final Map<BigInteger, UStoneSmokeSensorStatus> uStoneSmokeSensorStatusLast = new ConcurrentHashMap<>();

    private final  Map<BigInteger, UStoneAirSixSensorStatus> uStoneAirSixSensorStatusLast = new ConcurrentHashMap<>();

    private final  List<UStone10AOutlet> uStone10AOutlet5min = Collections.synchronizedList(new ArrayList<>());


    private final  DashAirVO dashAirVO = new DashAirVO() ;


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

    @Override
    public void putUStone10AOutlet(BigInteger id ,UStone10AOutlet uStone10AOutlet) {
        this.uStone10AOutletLast.put(id , uStone10AOutlet);
    }

    @Override
    public void putUStoneAirSixSensorStatus(BigInteger id , UStoneAirSixSensorStatus uStoneAirSixSensorStatus) {

    }

    @Override
    public void putUStoneSmokeSensorStatus(BigInteger id , UStoneSmokeSensorStatus uStoneSmokeSensorStatus) {

    }

    @Override
    public void updateCurrentAir(UStoneAirSixSensorStatus uStoneAirSixSensorStatus) {
        this.dashAirVO.setPs(uStoneAirSixSensorStatus.getParticleSize());
        this.dashAirVO.setCo2(uStoneAirSixSensorStatus.getCo2Ppm());
        this.dashAirVO.setHumidity(uStoneAirSixSensorStatus.getEnvHumidity());
        this.dashAirVO.setPm10(uStoneAirSixSensorStatus.getMassPm10());
        this.dashAirVO.setTemp(uStoneAirSixSensorStatus.getEnvTemperature());
        this.dashAirVO.setPm25(uStoneAirSixSensorStatus.getMassPm2_5());
        this.dashAirVO.setPm10(uStoneAirSixSensorStatus.getMassPm10());
        this.dashAirVO.setTvoc(uStoneAirSixSensorStatus.getTvocPpb());
        this.dashAirVO.setHumidity(" % ");
        this.dashAirVO.setTempUnit(" C ");
        this.dashAirVO.setTvocUnit( " PPB ");
        this.dashAirVO.setCo2Unit( " PPM ");
        this.dashAirVO.setPm10Unit( " ug/m3 ");
        this.dashAirVO.setPm25Unit( " ug/m3 ");
        this.dashAirVO.getLevel();

    }
}