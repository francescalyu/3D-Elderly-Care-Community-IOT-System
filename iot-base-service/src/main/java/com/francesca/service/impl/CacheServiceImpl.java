package com.francesca.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.DeviceDao;
import com.francesca.dao.ProductDao;
import com.francesca.dao.WarnDao;
import com.francesca.dao.WarnRuleDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.ProductEntity;
import com.francesca.model.DTO.WarnEntity;
import com.francesca.model.DTO.WarnRuleEntity;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.dash.DashAirVO;
import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;
import com.francesca.service.CacheService;
import com.francesca.service.DeviceMsg;
import com.francesca.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheServiceImpl implements CacheService {

    private final Map<BigInteger, Device> cache = new ConcurrentHashMap<>();

    private final Map<BigInteger, DeviceEntity> deviceEntityMap = new ConcurrentHashMap<>();


    private final Map<BigInteger, ProductEntity> productEntityMap = new ConcurrentHashMap<>();

    // deviceId, DeviceList
    private final Map<BigInteger, UStone10AOutlet> uStone10AOutletLast = new ConcurrentHashMap<>();

    private final Map<BigInteger, UStoneSmokeSensorStatus> uStoneSmokeSensorStatusLast = new ConcurrentHashMap<>();

    private final  Map<BigInteger, UStoneAirSixSensorStatus> uStoneAirSixSensorStatusLast = new ConcurrentHashMap<>();

    private final  Map<BigInteger, List<WarnRuleEntity>> warnRuleMap = new ConcurrentHashMap<>();

    private final Map<BigInteger, WarnEntity> warnMap = new ConcurrentHashMap<>();

    private static volatile   DashAirVO dashAirVO = new DashAirVO() ;

    private static volatile   DashPowerVO dashPowerVO = new DashPowerVO();


    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private DeviceMsg deviceMsg;

    @Autowired
    private WarnRuleDao warnRuleDao;

    @Autowired
    private WarnDao warnDao;

    @Autowired
    private ProductDao productDao;

    @PostConstruct
    public void initCache(){
        List<DeviceEntity> deviceEntityList = deviceDao.selectAll();
        if (ObjectUtil.isEmpty(deviceEntityList)){
            return;
        }
        deviceEntityList.stream().forEach(
                v-> {
                     cache.put(v.getId(), deviceMsg.setDevice(v) );
                     deviceEntityMap.put(v.getId(), v);
                }
        );

        List<ProductEntity> products  = productDao.selectAll();
        if (ObjectUtil.isEmpty(products)){
            return;
        }
        products.stream().forEach(
                v-> {
                    productEntityMap.put(v.getId(), v);
                }
        );

        List<WarnRuleEntity> warnRuleEntities = warnRuleDao.selectAll();

        if (ObjectUtil.isNotEmpty(warnRuleEntities)) {

            warnRuleEntities.stream().forEach(v -> {

               List<WarnRuleEntity> temp = warnRuleMap.get(BigInteger.valueOf(v.getProd()));
                if (ObjectUtil.isEmpty(temp)){
                    temp = new ArrayList<>();
                }
               temp.add(v);
               warnRuleMap.put(BigInteger.valueOf(v.getProd()), temp);

            });
        }

        List<WarnEntity> warnEntities = warnDao.selectAll();

        if (ObjectUtil.isNotEmpty(warnEntities)) {

            warnEntities.stream().forEach(v -> {

                if (ObjectUtil.isNotEmpty(v)) {

                    warnMap.put( v.getId(),v);
                }

            });
        }

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

       return   this.cache.values().stream()
                .filter(p -> p.getTopicUp() != null && upTopic.trim().equals(p.getTopicUp().trim()))
                .findFirst()
                .orElse(null);


    }

    @Override
    public DeviceEntity getDevice(BigInteger id) {
        return this.deviceEntityMap.get(id);
    }

    @Override
    public ProductEntity getProduct(BigInteger id) {
        return  this.productEntityMap.get(id);
    }

    @Override
    public void putUStone10AOutlet(BigInteger id ,UStone10AOutlet uStone10AOutlet) {
        if (this.uStone10AOutletLast.isEmpty()) {

             this.uStone10AOutletLast.put(id, uStone10AOutlet);
             return;
        }

        BigDecimal energy = new BigDecimal(uStone10AOutlet.getEnergyToday());

        if (energy.equals(0)) {

            UStone10AOutlet uStone10AOutlet1 =  this.uStone10AOutletLast.get(id);
            uStone10AOutlet.countMinEnergyWhen0();

            //add energy
            BigDecimal energy1 = new BigDecimal(uStone10AOutlet1.getEnergyToday());
            energy = energy.add(energy1);

            uStone10AOutlet.setEnergyToday(energy.toString());

            this.uStone10AOutletLast.put(id , uStone10AOutlet);
        }


    }

    @Override
    public List<UStone10AOutlet> getAllUStone10AOutletStatus() {
        return new ArrayList<>( this.uStone10AOutletLast.values());
    }

    @Override
    public ConcurrentHashMap<BigInteger, UStone10AOutlet> getUStone10AOutlets() {
        return (ConcurrentHashMap<BigInteger, UStone10AOutlet>) this.uStone10AOutletLast;
    }

    @Override
    public void putUStoneAirSixSensorStatus(BigInteger id , UStoneAirSixSensorStatus uStoneAirSixSensorStatus) {

        this.uStoneAirSixSensorStatusLast.put(id, uStoneAirSixSensorStatus);

    }

    @Override
    public ConcurrentHashMap<BigInteger, UStoneAirSixSensorStatus> getUstoneAirSixSensors() {
        return (ConcurrentHashMap<BigInteger, UStoneAirSixSensorStatus>) this.uStoneAirSixSensorStatusLast;
    }

    @Override
    public void putUStoneSmokeSensorStatus(BigInteger id , UStoneSmokeSensorStatus uStoneSmokeSensorStatus) {
        this.uStoneSmokeSensorStatusLast.put(id , uStoneSmokeSensorStatus);
    }

    @Override
    public ConcurrentHashMap<BigInteger, UStoneSmokeSensorStatus> getUstoneSmokeSensors() {
        return (ConcurrentHashMap<BigInteger, UStoneSmokeSensorStatus>) this.uStoneSmokeSensorStatusLast;
    }

    @Override
    public void updateCurrentAir(UStoneAirSixSensorStatus uStoneAirSixSensorStatus) {
        this.dashAirVO.setPs(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getParticleSize()));
        this.dashAirVO.setCo2(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getCo2Ppm()));
        this.dashAirVO.setHumidity(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getEnvHumidity()));
        this.dashAirVO.setPm10(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getMassPm10()));
        this.dashAirVO.setTemp(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getEnvTemperature()));
        this.dashAirVO.setPm25(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getMassPm2_5()));
        this.dashAirVO.setPm10(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getMassPm10()));
        this.dashAirVO.setTvoc(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getTvocPpb()));
        this.dashAirVO.setHumidity(CommonUtil.toNumberStr(uStoneAirSixSensorStatus.getEnvHumidity()));
        this.dashAirVO.setTempUnit(" C ");
        this.dashAirVO.setPm10Unit( " ug/m3");
        this.dashAirVO.setPm25Unit( " ug/m3" );
        this.dashAirVO.setPsUnit( "um" );
        this.dashAirVO.setTvocUnit( " PPB ");
        this.dashAirVO.setCo2Unit( " PPM ");
        this.dashAirVO.setHumidityUnit( " % ");
        this.dashAirVO.setTimestamp(DateUtil.now());
        this.dashAirVO.setLevel();

    }

    @Override
    public DashAirVO getCurrentAir() {
        return this.dashAirVO;
    }

    @Override
    public DashPowerVO getCurrentPower() {
        return this.dashPowerVO;
    }

    @Override
    public void putDashPower(DashPowerVO dashPowerVO) {
         this.dashPowerVO = dashPowerVO;
    }

    @Override
    public List<WarnRuleEntity> readWarnRule(BigInteger prodId, int closeOpen) {

        List<WarnRuleEntity> warnRuleEntities = this.warnRuleMap.get(prodId);

        List<WarnRuleEntity> out = new ArrayList<>();

        if (ObjectUtil.isNotEmpty(warnRuleEntities)){

            warnRuleEntities.stream().forEach(
                    v-> {
                           if (v.getClosewarn() == closeOpen){
                               out.add(v);
                           }
                    }
            );

        }

        return out;

    }

    @Override
    public WarnEntity readWarn(BigInteger warn) {
        return this.warnMap.get(warn);
    }
}