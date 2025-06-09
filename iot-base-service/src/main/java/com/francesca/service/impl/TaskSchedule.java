package com.francesca.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.mqtt.ustoneMsg.UStoneAirSixSensorStatus;
import com.francesca.mqtt.ustoneMsg.UStoneSmokeSensorStatus;
import com.francesca.service.CacheService;
import com.francesca.service.WarnRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskSchedule {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private WarnRuleService warnRuleService;


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
            power = power.add( new BigDecimal(uStone10AOutlets.get(i).getActivePower()));
            todayPower = todayPower.add( new BigDecimal(uStone10AOutlets.get(i).getEnergyToday()));

        }

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


}
