package com.francesca.service.impl;


import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.mqtt.ustoneMsg.UStone10AOutlet;
import com.francesca.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TaskSchedule {

    @Autowired
    private CacheService cacheService;


    // count power every 1 min
    @Scheduled(fixedRate = 1000 * 60)
    public void countPowerEverMin() {

        List<UStone10AOutlet> uStone10AOutlets = cacheService.getAllUStone10AOutletStatus();

        DashPowerVO dashPowerVO = cacheService.getCurrentPower();

        BigDecimal power = new BigDecimal(dashPowerVO.getCurrentPower());
        BigDecimal todayPower = new BigDecimal(dashPowerVO.getTodayPower());

        for ( int i =0 ; i<uStone10AOutlets.size() ; i++ ){
            power = power.add( new BigDecimal(uStone10AOutlets.get(i).getActivePower()));
            todayPower = todayPower.add( new BigDecimal(uStone10AOutlets.get(i).getEnergyToday()));

        }

        cacheService.putDashPower(dashPowerVO);

    }






}
