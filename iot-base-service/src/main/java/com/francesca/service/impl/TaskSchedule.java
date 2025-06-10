package com.francesca.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.PointDao;
import com.francesca.dao.WarnRecordDao;
import com.francesca.model.DTO.DeviceEntity;
import com.francesca.model.DTO.PointEntity;
import com.francesca.model.DTO.WarnRecordEntity;
import com.francesca.model.DTO.WarnRuleEntity;
import com.francesca.model.VO.dash.DashAirVO;
import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
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


    // count power every 1 min
    @Scheduled(fixedRate = 1000 * 60)
    public void countPowerEverMin() {

        List<UStone10AOutlet> uStone10AOutlets = cacheService.getAllUStone10AOutletStatus();

        DashPowerVO dashPowerVO = cacheService.getCurrentPower();

        BigDecimal power = new BigDecimal(0);

        BigDecimal todayPower = new BigDecimal(0);
        if (ObjectUtil.isNotEmpty(dashPowerVO.getTodayPower())) {
             todayPower = new BigDecimal(dashPowerVO.getTodayPower());
        }

        for ( int i =0 ; i<uStone10AOutlets.size() ; i++ ){

            UStone10AOutlet uStone10AOutlet = uStone10AOutlets.get(i);
            power = power.add( new BigDecimal(uStone10AOutlet.getActivePower()));

            BigDecimal temp = new BigDecimal(uStone10AOutlet.getEnergyToday());
            todayPower = todayPower.add( temp );

        }

        dashPowerVO.setCurrentPower(String.valueOf(power));
        dashPowerVO.setTodayPower(String.valueOf(todayPower));

        cacheService.putDashPower(dashPowerVO);

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
