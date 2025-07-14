package com.francesca.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.dao.*;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.dash.DashAirVO;
import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.model.VO.dash.HealthVO;
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
import java.util.Date;
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

    @Autowired
    private Health1hDao health1hDao;

    @Autowired
    private Power5minTotalDao power5minTotalDao;


    // count power every 1 hour
    @Scheduled(fixedRate = 1000 * 60 * 1)
    public void countPowerEverMin() {

        List<UStone10AOutlet> uStone10AOutlets = cacheService.getAllUStone10AOutletStatus();
        List<GeekOpen16AOutlet> geekOpen16AOutlets = new ArrayList<>(cacheService.getGeekOpen16AOutlet().values());
        List<UStone3WaySwitch> uStone3WaySwitches = new ArrayList<>(cacheService.getUStone3WaySwitch().values());

        DashPowerVO dashPowerVO = cacheService.getCurrentPower();

        BigDecimal power = new BigDecimal(0);

        BigDecimal todayPower = new BigDecimal(0);
        BigDecimal acPower = new BigDecimal(0);
        BigDecimal lightPower = new BigDecimal(0);

        for ( int i =0 ; i<uStone10AOutlets.size() ; i++ ){

            UStone10AOutlet uStone10AOutlet = uStone10AOutlets.get(i);
            power = power.add( new BigDecimal(uStone10AOutlet.getActivePower()));

            BigDecimal temp = uStone10AOutlet.getCount1minEnergy();
            log.info("device ustone 10a :" + i + "  1 min energy :"  + temp  );
            if(ObjectUtil.isNotEmpty(temp)) {
                todayPower = todayPower.add(temp);
            }else {
                todayPower = todayPower.add(new BigDecimal(uStone10AOutlet.getEnergyToday()));
            }
            //log.info("today power:" + todayPower);
        }

        //ac power
        for ( int i =0 ; i<geekOpen16AOutlets.size() ; i++ ){

            GeekOpen16AOutlet geekOpen16AOutlet = geekOpen16AOutlets.get(i);
            power = power.add( new BigDecimal(geekOpen16AOutlet.getPower()));

            BigDecimal temp = geekOpen16AOutlet.getCount1minEnergy();
            log.info("device geekopen 16a :" + i  + " 1min enerygy:" + temp);
            if(ObjectUtil.isNotEmpty(temp)) {
                todayPower = todayPower.add(temp);
                acPower = acPower.add(temp);
            }

        }

        for ( int i =0 ; i<uStone3WaySwitches.size() ; i++ ){

            UStone3WaySwitch uStone3WaySwitch = uStone3WaySwitches.get(i);
            power = power.add( new BigDecimal(uStone3WaySwitch.getActivePower()));

            BigDecimal temp =uStone3WaySwitch.getCount1minEnergy();

            //log.info("device 3way switch :" + i  + " 1min enerygy:" + temp);
            if (ObjectUtil.isNotEmpty(temp)) {
                todayPower = todayPower.add(temp);
                lightPower = lightPower.add(temp);
            }else {
                todayPower = todayPower.add(new BigDecimal(uStone3WaySwitch.getEnergyToday()));
                lightPower = lightPower.add(new BigDecimal(uStone3WaySwitch.getEnergyToday()));
            }

        }


        dashPowerVO.setCurrentPower(String.valueOf(power));
        dashPowerVO.setTodayPower(String.valueOf(todayPower));
        dashPowerVO.setAcPower(String.valueOf(acPower));
        dashPowerVO.setLightPower(String.valueOf(lightPower));

        BigDecimal epower = todayPower.subtract(acPower);

        epower = epower.subtract(lightPower);
        dashPowerVO.setElectPower(String.valueOf(epower));

        //log.info("today power:" + todayPower);

        cacheService.putDashPower(dashPowerVO);

    }


    //save power data to database every 5 mini
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void savePowerData() {

        DashPowerVO dashPower5minVO = cacheService.get5minPower();
        savePower(dashPower5minVO, 1);
        cacheService.set5minPower(cacheService.getCurrentPower());
        log.info("save power 5min:" + dashPower5minVO.getTodayPower());

    }

    //save power total data to database every 5 min
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void savePowerTotalData() {

       savePowerTotal(1);


    }

    //save power total data to database every 5 min
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void savePowerTotal1hData() {

        savePowerTotal(2);


    }



    private void savePowerTotal(int ptype){

        DashPowerVO dashPowerVO = cacheService.getCurrentPower();

        Power5minTotalEntity power5minTotalEntity = new Power5minTotalEntity();

        if (ObjectUtil.isNotEmpty(dashPowerVO)) {

            power5minTotalEntity.setId(BigInteger.valueOf(0));
            power5minTotalEntity.setCurrentpower(dashPowerVO.getCurrentPower());
            power5minTotalEntity.setEnergytoday(dashPowerVO.getTodayPower());
            power5minTotalEntity.setAcpower(dashPowerVO.getAcPower());
            power5minTotalEntity.setLightpower(dashPowerVO.getLightPower());
            power5minTotalEntity.setPowersave(dashPowerVO.getPowerSave());
            power5minTotalEntity.setCo2(dashPowerVO.getCo2());
            power5minTotalEntity.setTree(dashPowerVO.getTree());
            power5minTotalEntity.setElectpower(dashPowerVO.getElectPower());
            power5minTotalEntity.setPtype(ptype);
            power5minTotalEntity.setTime(new Date());


            power5minTotalDao.insert(power5minTotalEntity);

            if (ptype == 2) {

                log.info("save power total data 1 h :" + power5minTotalEntity.getEnergytoday());
            }else {
                log.info("save power total data 5 min :" + power5minTotalEntity.getEnergytoday());
            }
        }


    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void savePower1hData() {

        DashPowerVO dashPower1hVO = cacheService.get1hPower();
        savePower(dashPower1hVO, 2);
        cacheService.set1hPower(cacheService.getCurrentPower());
        log.info("save power 1h:" + dashPower1hVO.getTodayPower());

    }

    // 每天午夜执行  清零当日电量
    @Scheduled(cron = "58 59 12 * * ?")
    public void executeAtMidnight() {

        DashPowerVO dashPower5minVO = cacheService.get5minPower();
        dashPower5minVO.setTodayPower(null);

        savePower(dashPower5minVO, 3);

        DashPowerVO dashPowerVO = cacheService.getCurrentPower();
        log.info(" start clear today power : " + dashPowerVO.getTodayPower() );
        dashPowerVO.setTodayPower("0");
        dashPowerVO.setCurrentPower("0");
        dashPowerVO.setLightPower("0");
        dashPowerVO.setElectPower("0");
        dashPowerVO.setAcPower("0");

       cacheService.set5minPower(dashPowerVO);
       cacheService.set1hPower(dashPowerVO);

        log.info(" save & clear today power : " + dashPowerVO.getTodayPower() );

    }


    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void saveHealth1hData() {

        HealthVO healthVO = cacheService.getHealth();

        HealthVO health1hVO = cacheService.getHealth1h();

        if (ObjectUtil.isNotEmpty(healthVO) && ObjectUtil.isNotEmpty(healthVO.getHeart_rate())) {

            if (ObjectUtil.isEmpty(health1hVO) || ObjectUtil.isEmpty(health1hVO.getTotal_steps())) {

                Health1hEntity health1hEntity = new Health1hEntity();

                health1hEntity.setBloodpressure(healthVO.getBlood_pressure());
                health1hEntity.setStep(healthVO.getTotal_steps());
                health1hEntity.setBodytemp(healthVO.getBody_temperature());
                health1hEntity.setHeartrate(healthVO.getHeart_rate());
                health1hEntity.setCalories(healthVO.getTotal_calories());
                health1hEntity.setSleep(healthVO.getTotal_sleep());
                health1hEntity.setTime(new Date());

                health1hDao.insert(health1hEntity);

            } else {

                Health1hEntity health1hEntity = new Health1hEntity();
                health1hEntity.setBloodpressure(healthVO.getBlood_pressure());
                health1hEntity.setBodytemp(healthVO.getBody_temperature());
                health1hEntity.setHeartrate(healthVO.getHeart_rate());
                health1hEntity.setSleep(countPower(healthVO.getTotal_sleep(), health1hVO.getTotal_sleep()));
                health1hEntity.setStep(countPower(healthVO.getTotal_steps(), health1hVO.getTotal_steps()));
                health1hEntity.setCalories(countPower(healthVO.getTotal_calories(), health1hVO.getTotal_calories()));
                health1hEntity.setTime(new Date());

                health1hDao.insert(health1hEntity);

            }

            cacheService.setHealth1h(healthVO);
            log.info("save health data 1 hour, step:" + healthVO.getTotal_steps());
        }

    }


    private void  savePower (DashPowerVO timePower , int type ){

        if (ObjectUtil.isNotEmpty(cacheService.getCurrentPower().getTodayPower())  ) {

            DashPowerVO dashPowerVO = cacheService.getCurrentPower();


            double power = Double.parseDouble(dashPowerVO.getTodayPower());
            Power5minEntity power5minEntity = new Power5minEntity();



            if ( ObjectUtil.isEmpty(timePower.getTodayPower()) || power == 0  ) {

                power5minEntity.setId(BigInteger.valueOf(0));
                power5minEntity.setCurrentpower(dashPowerVO.getCurrentPower());
                power5minEntity.setEnergytoday(dashPowerVO.getTodayPower());
                power5minEntity.setAcpower(dashPowerVO.getAcPower());
                power5minEntity.setLightpower(dashPowerVO.getLightPower());
                power5minEntity.setPowersave(dashPowerVO.getPowerSave());
                power5minEntity.setCo2(dashPowerVO.getCo2());
                power5minEntity.setTree(dashPowerVO.getTree());
                power5minEntity.setElectpower(dashPowerVO.getElectPower());
                power5minEntity.setPtype(type);
                power5minEntity.setTime(new Date());

                power5minDao.insert(power5minEntity);


            }else {

                power5minEntity.setId(BigInteger.valueOf(0));
                power5minEntity.setCurrentpower(dashPowerVO.getCurrentPower());
                power5minEntity.setEnergytoday( countPower(dashPowerVO.getTodayPower(), timePower.getTodayPower()));
                power5minEntity.setAcpower(countPower(dashPowerVO.getAcPower(), timePower.getAcPower()));
                power5minEntity.setLightpower(countPower(dashPowerVO.getLightPower(), timePower.getLightPower()));
                power5minEntity.setPowersave(dashPowerVO.getPowerSave());
                power5minEntity.setCo2(dashPowerVO.getCo2());
                power5minEntity.setTree(dashPowerVO.getTree());
                power5minEntity.setElectpower(countPower(dashPowerVO.getElectPower(),timePower.getElectPower()));
                power5minEntity.setPtype(type);
                power5minEntity.setTime(new Date());

                power5minDao.insert(power5minEntity);

            }
        }


    }

    private String countPower(String power, String power5min ){

       if(ObjectUtil.isEmpty(power)){
           power = "0";
       }

       if(ObjectUtil.isEmpty(power5min)){
           power5min = "0";
       }

        BigDecimal pn = new BigDecimal(power);
        BigDecimal pn5 = new BigDecimal(power5min);

        if (pn.compareTo(pn5) <= 0 ){
            return "0";
        }

        return String.valueOf(pn.subtract(pn5));


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
