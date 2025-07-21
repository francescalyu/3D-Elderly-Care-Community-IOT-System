package com.francesca.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.*;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.dash.*;
import com.francesca.mqtt.bluetouth.ButtonEvent;
import com.francesca.mqtt.bluetouth.DoorSensorEvent;
import com.francesca.mqtt.bluetouth.HealthBandEvent;
import com.francesca.mqtt.bluetouth.LightSensorEvent;
import com.francesca.mqtt.geekopen.GeekOpen16AOutlet;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStone3WaySwitch;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;
import com.francesca.service.CacheService;
import com.francesca.service.DeviceMsg;
import com.francesca.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CacheServiceImpl implements CacheService {

    private final Map<BigInteger, Device> cache = new ConcurrentHashMap<>();

    private final Map<BigInteger, DeviceEntity> deviceEntityMap = new ConcurrentHashMap<>();

    private final Map<BigInteger, PointEntity> pointEntityMap = new ConcurrentHashMap<>();

    private final Map<BigInteger, ProductEntity> productEntityMap = new ConcurrentHashMap<>();

    // deviceId, DeviceList
    private final Map<BigInteger, UStone10AOutlet> uStone10AOutletLast = new ConcurrentHashMap<>();

    private final Map<BigInteger, GeekOpen16AOutlet> geekOpen16AOutletLast = new ConcurrentHashMap<>();

    private final Map<BigInteger, UStone3WaySwitch> uStone3WaySwitchLast = new ConcurrentHashMap<>();

    private final Map<BigInteger, UStoneSmokeSensorStatus> uStoneSmokeSensorStatusLast = new ConcurrentHashMap<>();

    private final  Map<BigInteger, UStoneAirSixSensorStatus> uStoneAirSixSensorStatusLast = new ConcurrentHashMap<>();

    private final Map<BigInteger, ButtonEvent> uStoneBlueToothButtonLast = new ConcurrentHashMap<>();

    private final Map<BigInteger, DoorSensorEvent> uStoneDoorSensorLast = new ConcurrentHashMap<>();

    private final Map<BigInteger,HealthBandEvent> uStoneHealthBandLast = new ConcurrentHashMap<>();

    private final Map<BigInteger,LightSensorEvent> uStoneLightSensor = new ConcurrentHashMap<>();

    private final  Map<BigInteger, List<WarnRuleEntity>> warnRuleMap = new ConcurrentHashMap<>();

    private final Map<BigInteger, WarnEntity> warnMap = new ConcurrentHashMap<>();

    private static volatile   DashAirVO dashAirVO = new DashAirVO() ;

    private static volatile   DashPowerVO dashPowerVO = new DashPowerVO();

    private static volatile  DashPowerVO dashPower5minVO = new DashPowerVO();

    private static volatile  DashPowerVO dashPower1hVO = new DashPowerVO();

    private static volatile HealthVO healthVO = new HealthVO();

    private static volatile HealthVO health1hVO = null;

    private static volatile   DashDoorVO dashDoorVO = new DashDoorVO();


    private static volatile DashSosVO dashSosVO = new DashSosVO();

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private DeviceMsg deviceMsg;

    @Autowired
    private WarnRuleDao warnRuleDao;

    @Autowired
    private WarnDao warnDao;

    @Autowired
    private PointDao pointDao;

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

        List<PointEntity> pointEntities  = pointDao.selectAll();
        if (ObjectUtil.isEmpty(pointEntities)){
            return;
        }
        pointEntities.stream().forEach(
                v-> {
                    pointEntityMap.put(v.getId(), v);
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
    public List<Device> getAllDevice() {
        return new ArrayList<>( this.cache.values() );
    }

    @Override
    public ProductEntity getProduct(BigInteger id) {
        return  this.productEntityMap.get(id);
    }

    @Override
    public PointEntity getPoint(BigInteger id) {
        return this.pointEntityMap.get(id);
    }

    @Override
    public void putUStone10AOutlet(BigInteger id ,UStone10AOutlet uStone10AOutlet) {
        if (this.uStone10AOutletLast.isEmpty()) {

             uStone10AOutlet.countMinEnergyWhen0();
             this.uStone10AOutletLast.put(id, uStone10AOutlet);
             return;
        }

            UStone10AOutlet uStone10AOutlet1 =  this.uStone10AOutletLast.get(id);
            if (ObjectUtil.isEmpty(uStone10AOutlet1)){
                uStone10AOutlet.countMinEnergyWhen0();
                this.uStone10AOutletLast.put(id, uStone10AOutlet);
                return;
            }

            //exec power 0 error outlets;
            //add energy
        BigDecimal energy = new BigDecimal(uStone10AOutlet.getEnergyToday());
        if (energy.compareTo(BigDecimal.ZERO) == 0) {

            uStone10AOutlet.countMinEnergyWhen0();
            BigDecimal temp = uStone10AOutlet1.getCount1minEnergy();
            log.info( "ustone 10a  "  +  "last 1 min energy" + temp + "   power :" + uStone10AOutlet1.getActivePower());

            if (ObjectUtil.isEmpty(temp)){
                temp = new BigDecimal(0);
            }
            temp = temp.add(uStone10AOutlet.getCount1minEnergy());

            log.info( "ustone 10a  "  +  " 1 min energy" + temp + "   power :" + uStone10AOutlet.getActivePower());
            uStone10AOutlet.setCount1minEnergy(temp);

        }
        this.uStone10AOutletLast.put(id , uStone10AOutlet);

    }

    @Override
    public void putGeekOpen16AOutlet(BigInteger id, GeekOpen16AOutlet geekOpen16AOutlet) {
        if (this.geekOpen16AOutletLast.isEmpty()) {

            geekOpen16AOutlet.countMinEnergyWhen0();
            this.geekOpen16AOutletLast.put(id, geekOpen16AOutlet);
            return;
        }

        GeekOpen16AOutlet geekOpen16AOutlet1 =  this.geekOpen16AOutletLast.get(id);
        if (ObjectUtil.isEmpty(geekOpen16AOutlet1)){
            geekOpen16AOutlet.countMinEnergyWhen0();
            this.geekOpen16AOutletLast.put(id, geekOpen16AOutlet);
            return;
        }



            geekOpen16AOutlet.countMinEnergyWhen0();
            BigDecimal temp = geekOpen16AOutlet1.getCount1minEnergy();
            if (ObjectUtil.isEmpty(temp)){
                temp = new BigDecimal(0);
            }

             log.info( "geek 16a "  +  "last 1 min energy" + temp + "   power :" + geekOpen16AOutlet.getPower());

            temp = temp.add(geekOpen16AOutlet.getCount1minEnergy());
            geekOpen16AOutlet.setCount1minEnergy(  temp );

            BigDecimal today = geekOpen16AOutlet.getEnergyToday();
            today = today.add(geekOpen16AOutlet1.getEnergyToday());

          log.info( "geek 16a "  +  "1 min energy" + temp + "   power :" + geekOpen16AOutlet.getPower());

        geekOpen16AOutlet.setEnergyToday(today );

        //renew the device data
        this.geekOpen16AOutletLast.put(id , geekOpen16AOutlet);
    }

    @Override
    public void putUStone3WaySwitch(BigInteger id, UStone3WaySwitch uStone3WaySwitch) {
        if (this.uStone3WaySwitchLast.isEmpty()) {

            uStone3WaySwitch.countMinEnergyWhen0();
            this.uStone3WaySwitchLast.put(id, uStone3WaySwitch);
            return;
        }

        UStone3WaySwitch uStone3WaySwitch1 =  this.uStone3WaySwitchLast.get(id);

        if (ObjectUtil.isEmpty(uStone3WaySwitch1)){
            uStone3WaySwitch.countMinEnergyWhen0();
            this.uStone3WaySwitchLast.put(id, uStone3WaySwitch);
            return;
        }

        //exec power 0 error outlets;
        //add energy
        BigDecimal energy = new BigDecimal(uStone3WaySwitch.getEnergyToday());
        if (energy.compareTo(BigDecimal.ZERO) == 0) {

            uStone3WaySwitch.countMinEnergyWhen0();
            BigDecimal temp = uStone3WaySwitch1.getCount1minEnergy();

            log.info( "3way switch" + id +  "last 1 min" + temp + "  power :" + uStone3WaySwitch1.getActivePower());

            if(ObjectUtil.isEmpty(temp)){
                temp = new BigDecimal(0);
            }
            //加上上一次设备记录对象的1分钟电量值

            temp = temp.add(uStone3WaySwitch.getCount1minEnergy());
            uStone3WaySwitch.setCount1minEnergy(temp);
            log.info( "3way switch" + id +  "new 1 min energy" + temp + "  power :" + uStone3WaySwitch.getActivePower());

        }

        this.uStone3WaySwitchLast.put(id , uStone3WaySwitch);
    }

    @Override
    public ConcurrentHashMap<BigInteger, UStone3WaySwitch> getUStone3WaySwitch() {
        return (ConcurrentHashMap<BigInteger, UStone3WaySwitch>) this.uStone3WaySwitchLast;
    }

    @Override
    public ConcurrentHashMap<BigInteger, GeekOpen16AOutlet> getGeekOpen16AOutlet() {
        return (ConcurrentHashMap<BigInteger, GeekOpen16AOutlet>) this.geekOpen16AOutletLast;
    }

    private UStone10AOutlet execPower0(UStone10AOutlet uStone10AOutlet){
        BigDecimal energy = new BigDecimal(uStone10AOutlet.getEnergyToday());
        if (energy.compareTo(BigDecimal.ZERO) == 0) {
            uStone10AOutlet.countMinEnergyWhen0();
        }
        return uStone10AOutlet;
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
    public void updateSmoke(DashAirVO dishair) {
        this.dashAirVO.setSmokeLevel(dishair.getSmokeLevel());
        this.dashAirVO.setLpgLevel(dishair.getLpgLevel());
        this.dashAirVO.setCoLevel(dishair.getCoLevel());
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
    public DashPowerVO get5minPower() {
        return this.dashPower5minVO;
    }

    @Override
    public DashPowerVO get1hPower() {
        return  dashPower1hVO;
    }

    @Override
    public void  set5minPower(DashPowerVO in){

        dashPower5minVO.setTodayPower(in.getTodayPower());
        dashPower5minVO.setAcPower(in.getAcPower());
        dashPower5minVO.setElectPower(in.getElectPower());
        dashPower5minVO.setCurrentPower(in.getCurrentPower());
        dashPower5minVO.setLightPower(in.getLightPower());
        dashPower5minVO.setCo2(in.getCo2());
        dashPower5minVO.setTree(in.getTree());
        dashPower5minVO.setPowerSave(in.getPowerSave());

    }

    @Override
    public void  set1hPower(DashPowerVO in){

        dashPower1hVO.setTodayPower(in.getTodayPower());
        dashPower1hVO.setAcPower(in.getAcPower());
        dashPower1hVO.setElectPower(in.getElectPower());
        dashPower1hVO.setCurrentPower(in.getCurrentPower());
        dashPower1hVO.setLightPower(in.getLightPower());
        dashPower1hVO.setCo2(in.getCo2());
        dashPower1hVO.setTree(in.getTree());
        dashPower1hVO.setPowerSave(in.getPowerSave());

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

    @Override
    public DashDoorVO getDashDoor() {
        return this.dashDoorVO;
    }

    @Override
    public void setDashDoor(DashDoorVO dashDoor) {

        this.dashDoorVO.setChangeDate(dashDoor.getChangeDate());
        this.dashDoorVO.setStatus(dashDoor.getStatus());
        this.dashDoorVO.setTemperature(dashDoor.getTemperature());
        this.dashDoorVO.setHumidity(dashDoor.getHumidity());
        this.dashDoorVO.setIlluminance(dashDoor.getIlluminance());
    }

    @Override
    public HealthVO getHealth() {
        return this.healthVO;
    }

    @Override
    public HealthVO getHealth1h() {
        return this.health1hVO;
    }

    @Override
    public void setHealth(HealthVO healthVO) {
        this.healthVO.setTotal_steps(healthVO.getTotal_steps());
        this.healthVO.setOnline("1");
        this.healthVO.setBlood_pressure(healthVO.getBlood_pressure());
        this.healthVO.setHeart_rate(healthVO.getHeart_rate());
        this.healthVO.setFalling_alert(healthVO.getFalling_alert());
        this.healthVO.setBody_temperature(healthVO.getBody_temperature());
        this.healthVO.setTotal_calories(healthVO.getTotal_calories());
        this.healthVO.setUpdateTime(DateUtil.now());
        this.healthVO.setTotal_sleep(healthVO.getTotal_sleep());
        this.healthVO.setNot_wearing_alert(healthVO.getNot_wearing_alert());


    }

    @Override
    public void setHealth1h(HealthVO healthVO) {
        if (ObjectUtil.isEmpty(health1hVO)){
            health1hVO = new HealthVO();
        }


        this.health1hVO.setTotal_steps(healthVO.getTotal_steps());
        this.health1hVO.setOnline("1");
        this.health1hVO.setBlood_pressure(healthVO.getBlood_pressure());
        this.health1hVO.setHeart_rate(healthVO.getHeart_rate());
        this.health1hVO.setFalling_alert(healthVO.getFalling_alert());
        this.health1hVO.setBody_temperature(healthVO.getBody_temperature());
        this.health1hVO.setTotal_calories(healthVO.getTotal_calories());
        this.health1hVO.setUpdateTime(DateUtil.now());
        this.health1hVO.setTotal_sleep(healthVO.getTotal_sleep());
        this.health1hVO.setNot_wearing_alert(healthVO.getNot_wearing_alert());


    }

    @Override
    public DashSosVO getSos() {
        return this.dashSosVO;
    }

    @Override
    public void setSos(DashSosVO dashSosVO) {

        this.dashSosVO.setSosTime(dashSosVO.getSosTime());
        this.dashSosVO.setStatus(dashSosVO.getStatus());
        this.dashSosVO.setCount(dashSosVO.getCount());
        this.dashSosVO.setPressTime(dashSosVO.getPressTime());

    }

    @Override
    public void putUStoneHealthBand(BigInteger devid, HealthBandEvent healthBandEvent) {
        this.uStoneHealthBandLast.put(devid,healthBandEvent);
    }

    @Override
    public ConcurrentHashMap<BigInteger, HealthBandEvent> getUStoneHealthBand() {
        return (ConcurrentHashMap<BigInteger, HealthBandEvent>) this.uStoneHealthBandLast;
    }

    @Override
    public void putUStoneDoorSensor(BigInteger devid, DoorSensorEvent doorSensorEvent) {
        this.uStoneDoorSensorLast.put(devid,doorSensorEvent);
    }

    @Override
    public ConcurrentHashMap<BigInteger, DoorSensorEvent> getUStoneDoorSensor() {
        return (ConcurrentHashMap<BigInteger, DoorSensorEvent>) this.uStoneDoorSensorLast;
    }


    @Override
    public void putUStoneLightSensor(BigInteger devid, LightSensorEvent lightSensorEvent) {
       this.uStoneLightSensor.put(devid,lightSensorEvent);
    }

    @Override
    public ConcurrentHashMap<BigInteger, LightSensorEvent> getUStoneLightSensor() {
        return (ConcurrentHashMap<BigInteger, LightSensorEvent>) this.uStoneLightSensor;
    }

    @Override
    public void putUStoneBlueToothButton(BigInteger devid, ButtonEvent buttonEvent) {
      this.uStoneBlueToothButtonLast.put(devid,buttonEvent);
    }

    @Override
    public ConcurrentHashMap<BigInteger, ButtonEvent> getUStoneBlueToothButton() {
        return (ConcurrentHashMap<BigInteger, ButtonEvent>) this.uStoneBlueToothButtonLast;
    }


}