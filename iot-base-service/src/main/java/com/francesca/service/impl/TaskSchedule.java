package com.francesca.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.PointDao;
import com.francesca.dao.Power5minDao;
import com.francesca.dao.WarnRecordDao;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.dash.DashAirVO;
import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.mqtt.geekopen.GeekOpen16AOutlet;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStone3WaySwitch;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;
import com.francesca.service.CacheService;
import com.francesca.service.CommonService;
import com.francesca.service.WarnRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TaskSchedule {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private WarnRuleService warnRuleService;

    @Autowired
    private WarnRecordDao warnRecordDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private Power5minDao power5minDao;


    // count power every 1 min
    @Scheduled(fixedRate = 1000 * 60)
    public void countPowerEverMin() {

        List<UStone10AOutlet> uStone10AOutlets = cacheService.getAllUStone10AOutletStatus();
        List<GeekOpen16AOutlet> geekOpen16AOutlets = new ArrayList<>(cacheService.getGeekOpen16AOutlet().values());
        List<UStone3WaySwitch> uStone3WaySwitches = new ArrayList<>(cacheService.getUStone3WaySwitch().values());

        DashPowerVO dashPowerVO = cacheService.getCurrentPower();

        BigDecimal power = new BigDecimal(0);

        BigDecimal todayPower = new BigDecimal(0);
        BigDecimal acPower = new BigDecimal(0);
        BigDecimal lightPower = new BigDecimal(0);

        if (ObjectUtil.isNotEmpty(dashPowerVO.getTodayPower())) {
             todayPower = new BigDecimal(dashPowerVO.getTodayPower());
        }

        if (ObjectUtil.isNotEmpty(dashPowerVO.getAcPower())){
            acPower = new BigDecimal(dashPowerVO.getAcPower());
        }

        if (ObjectUtil.isNotEmpty(dashPowerVO.getLightPower())){
            lightPower = new BigDecimal(dashPowerVO.getLightPower());
        }


        for ( int i =0 ; i<uStone10AOutlets.size() ; i++ ){

            UStone10AOutlet uStone10AOutlet = uStone10AOutlets.get(i);
            power = power.add( new BigDecimal(uStone10AOutlet.getActivePower()));

            BigDecimal temp = uStone10AOutlet.getCount1minEnergy();
            if(ObjectUtil.isNotEmpty(temp)) {
                todayPower = todayPower.add(temp);
            }

        }

        //ac power
        for ( int i =0 ; i<geekOpen16AOutlets.size() ; i++ ){

            GeekOpen16AOutlet geekOpen16AOutlet = geekOpen16AOutlets.get(i);
            power = power.add( new BigDecimal(geekOpen16AOutlet.getPower()));

            BigDecimal temp = geekOpen16AOutlet.getCount1minEnergy();
            if(ObjectUtil.isNotEmpty(temp)) {
                todayPower = todayPower.add(temp);
                acPower = acPower.add(temp);
            }

        }

        for ( int i =0 ; i<uStone3WaySwitches.size() ; i++ ){

            UStone3WaySwitch uStone3WaySwitch = uStone3WaySwitches.get(i);
            power = power.add( new BigDecimal(uStone3WaySwitch.getActivePower()));

            BigDecimal temp =uStone3WaySwitch.getCount1minEnergy();
            if (ObjectUtil.isNotEmpty(temp)) {
                todayPower = todayPower.add(temp);
                lightPower = lightPower.add(temp);
            }

        }


        dashPowerVO.setCurrentPower(String.valueOf(power));
        dashPowerVO.setTodayPower(String.valueOf(todayPower));
        dashPowerVO.setAcPower(String.valueOf(acPower));
        dashPowerVO.setLightPower(String.valueOf(lightPower));
        dashPowerVO.setElectPower(String.valueOf(todayPower.subtract(acPower).subtract(lightPower)));

        cacheService.putDashPower(dashPowerVO);

    }


    //save power data to database every 5 mini
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void savePowerData() {

        if (ObjectUtil.isNotEmpty(cacheService.getCurrentPower().getTodayPower())) {

            Power5minEntity power5minEntity = new Power5minEntity();

            power5minEntity.setId(BigInteger.valueOf(0));
            power5minEntity.setCurrentpower(cacheService.getCurrentPower().getCurrentPower());
            power5minEntity.setEnergytoday(cacheService.getCurrentPower().getTodayPower());
            power5minEntity.setAcpower(cacheService.getCurrentPower().getAcPower());
            power5minEntity.setLightpower(cacheService.getCurrentPower().getLightPower());
            power5minEntity.setPowersave(cacheService.getCurrentPower().getPowerSave());
            power5minEntity.setCo2(cacheService.getCurrentPower().getCo2());
            power5minEntity.setTree(cacheService.getCurrentPower().getTree());
            power5minEntity.setElectpower(cacheService.getCurrentPower().getElectPower());

            power5minDao.insert(power5minEntity);
        }
    }

    //exec warn rule every 30 seconds
    @Scheduled(fixedRate = 1000 * 30)
    public void execWarnRule() {

      ConcurrentHashMap<BigInteger, UStone10AOutlet> uStone10AOutlets =  cacheService.getUStone10AOutlets();

      if(ObjectUtil.isNotEmpty(uStone10AOutlets)) {
          uStone10AOutlets.entrySet().forEach(v -> {
                      //first close warn ,then open warn
                      warnRuleService.execWarnRule(v.getKey(), BigInteger.valueOf(2),  v.getValue(), 0);
                      warnRuleService.execWarnRule(v.getKey(), BigInteger.valueOf(2), v.getValue(), 1);
                  }
          );
      }

      ConcurrentHashMap<BigInteger, UStoneAirSixSensorStatus> uStoneAirSixSensors = cacheService.getUstoneAirSixSensors();

      if (ObjectUtil.isNotEmpty(uStoneAirSixSensors)){
          uStoneAirSixSensors.entrySet().forEach(v-> {
              warnRuleService.execWarnRule(v.getKey(), BigInteger.valueOf(3) , v.getValue(), 0);
              warnRuleService.execWarnRule(v.getKey(), BigInteger.valueOf(3) , v.getValue(),1);
          });


      }

      ConcurrentHashMap<BigInteger, UStoneSmokeSensorStatus> uStoneSmokeSensors = cacheService.getUstoneSmokeSensors();

      if(ObjectUtil.isNotEmpty(uStoneSmokeSensors)){
          uStoneSmokeSensors.entrySet().forEach(v -> {
              warnRuleService.execWarnRule(v.getKey(),BigInteger.valueOf(4) , v.getValue(), 0);
              warnRuleService.execWarnRule(v.getKey(),BigInteger.valueOf(4) , v.getValue(),1);
          });
      }
    }

    //exec smoke status
    @Scheduled(fixedRate = 1000 * 60)
    public void execSmokeStatus() {
        DashAirVO dashAirVO = cacheService.getCurrentAir();

         ConcurrentHashMap<BigInteger, UStoneSmokeSensorStatus> smokers = cacheService.getUstoneSmokeSensors();

         if (ObjectUtil.isNotEmpty(smokers)){
             UStoneSmokeSensorStatus uStoneSmokeSensorStatus = smokers.values().iterator().next();
             dashAirVO.setSmoke(String.valueOf(uStoneSmokeSensorStatus.getSmokePpm()));
             dashAirVO.setLpg(String.valueOf(uStoneSmokeSensorStatus.getLpgPpm()));
             dashAirVO.setCo(String.valueOf(uStoneSmokeSensorStatus.getCoPpm()));
         }

        dashAirVO.setLpgLevel("0");
        dashAirVO.setCoLevel("0");
        dashAirVO.setSmokeLevel("0");

        // read open warn and subsys = 3 ( env )
        List<WarnRecordEntity> warnRecordEntities = warnRecordDao.selectBySubsys(3, 2);

        if(ObjectUtil.isEmpty(warnRecordEntities)){

            return;
        }

        Map<Integer, List<PointEntity>> listMap = commonService.getPointByWarnRule();

        for(WarnRecordEntity warnRecordEntity:warnRecordEntities){

            List<PointEntity> pointEntities = listMap.get(warnRecordEntity.getRuleid().intValue());

            if (ObjectUtil.isNotEmpty(pointEntities)){

                List<BigInteger> ps = pointEntities.stream().map(PointEntity::getId).collect(Collectors.toList());

                if(ps.contains(BigInteger.valueOf(15))){
                     dashAirVO.setLpgLevel(String.valueOf(warnRecordEntity.getLevel()));
                }

                if(ps.contains(BigInteger.valueOf(16))){
                    dashAirVO.setCoLevel(String.valueOf(warnRecordEntity.getLevel()));
                }

                if(ps.contains(BigInteger.valueOf(17))){
                    dashAirVO.setSmokeLevel(String.valueOf(warnRecordEntity.getLevel()));
                }

            }


        }

        cacheService.updateSmoke(dashAirVO);

    }


}
