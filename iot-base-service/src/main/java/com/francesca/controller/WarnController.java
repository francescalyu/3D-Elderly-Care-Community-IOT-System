package com.francesca.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.*;
import com.francesca.model.DTO.*;
import com.francesca.model.VO.Device.Device;
import com.francesca.model.VO.Warn.EnergySavingTotal;
import com.francesca.model.VO.Warn.WarnTotal;
import com.francesca.model.VO.dash.WarnRecordVO;
import com.francesca.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


/**
 * @Author francesca

 * 2025-06-06
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Warn)
@Validated
@Api(tags = "告警")
public class WarnController {

    @Autowired
    private WarnDao warnDao;

    @Autowired
    private WarnRecordDao warnRecordDao;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private SubsysDao subsysDao;

    @Autowired
    private WarnRuleDao warnRuleDao;


    @ApiOperation(value = "列出所有告警定义")
    @GetMapping( "getall")
    public List<WarnEntity> getalldto() {

        return warnDao.selectAll();
    }


    @ApiOperation(value = "新增告警定义  (id 送 告警规则的 warnid ) 如没有送0 ")
    @PostMapping( "add")
    public Integer add(@RequestBody WarnEntity entity) {

        if (ObjectUtil.isNotEmpty(entity) && ObjectUtil.isNotEmpty(entity.getId())){
            WarnEntity warn = warnDao.selectByUid(entity.getId());

            if (ObjectUtil.isNotEmpty(warn)){
                return  -1;
            }
        }

        if (ObjectUtil.isEmpty(entity) || ObjectUtil.isEmpty(entity.getName()) || ObjectUtil.isEmpty(entity.getLevel())){
            return -1;
        }

        warnDao.insert(entity);

        List<WarnEntity> finds = warnDao.selectAll();
        Optional<BigInteger> maxId = finds.stream()
                .map(WarnEntity::getId)
                .max(Comparator.naturalOrder());

        int max = maxId.orElse(new BigInteger("-1")).intValue(); // 如果列表为空则返回0

       return max;

    }

    @ApiOperation(value = "删除告警定义  (id 送 告警规则的 warnid ) ")
    @DeleteMapping( "del")
    public boolean del(@RequestParam Integer warnid) {

       return warnDao.delete(BigInteger.valueOf(warnid));

    }


    @ApiOperation(value = "列出当月所有告警  参数 YYYYMM ")
    @GetMapping( "getMonthWarn")
    public List<WarnRecordVO> getMonthWarn(@RequestParam String yearMonth) {

        if (ObjectUtil.isEmpty(yearMonth) || yearMonth.length() < 5){
            return null;
        }

       List<WarnRecordEntity> warnRecordEntities =  warnRecordDao.selectByMonth(yearMonth);
        List<WarnRuleEntity> rules = warnRuleDao.selectAll();

        if(ObjectUtil.isEmpty(warnRecordEntities)){
            return null;
        }

        List<WarnRecordVO> warns = new ArrayList<>();

        for (WarnRecordEntity warnRecordEntity : warnRecordEntities){

            if (ObjectUtil.isNotEmpty(warnRecordEntity)  ){
                WarnRuleEntity rule = rules.stream().filter(v -> v.getId().equals(warnRecordEntity.getRuleid())).findFirst().orElse(null);

                if (ObjectUtil.isNotEmpty(rule)){
                    warns.add(setWarnRecordVO(warnRecordEntity , rule.getType() ));
                }
            }
        }
        return warns;

    }

    @ApiOperation(value = "列出当月所有节能  参数 YYYYMM ")
    @GetMapping( "getMonthEnergySaving")
    public List<WarnRecordVO> getMonthEnergySaving(@RequestParam String yearMonth) {

        if (ObjectUtil.isEmpty(yearMonth) || yearMonth.length() < 5){
            return null;
        }

        List<WarnRecordEntity> warnRecordEntities =  warnRecordDao.selectByMonth(yearMonth);
        List<WarnRuleEntity> rules = warnRuleDao.selectAll();

        if(ObjectUtil.isEmpty(warnRecordEntities)){
            return null;
        }

        List<WarnRecordVO> warns = new ArrayList<>();

        for (WarnRecordEntity warnRecordEntity : warnRecordEntities){

            if (ObjectUtil.isNotEmpty(warnRecordEntity)  ){
                WarnRuleEntity rule = rules.stream().filter(v -> v.getId().equals(warnRecordEntity.getRuleid())).findFirst().orElse(null);

                if (ObjectUtil.isNotEmpty(rule) && rule.getType() == 2){
                    warns.add(setWarnRecordVO(warnRecordEntity , rule.getType() ));
                }
            }
        }
        return warns;

    }


    @ApiOperation(value = "列出当日所有告警")
    @GetMapping( "getTodayWarn")
    public List<WarnRecordVO> getTodayWarn() {

        List<WarnRecordEntity> warnRecordEntities =  warnRecordDao.selectByDate(LocalDate.now());
        List<WarnRuleEntity> rules = warnRuleDao.selectAll();

        if(ObjectUtil.isEmpty(warnRecordEntities)){
            return null;
        }

        List<WarnRecordVO> warns = new ArrayList<>();

        for (WarnRecordEntity warnRecordEntity : warnRecordEntities){

            if (ObjectUtil.isNotEmpty(warnRecordEntity) &&  DateUtil.isSameDay(warnRecordEntity.getCreateTime(), DateUtil.date()) ){
                WarnRuleEntity rule = rules.stream().filter(v -> v.getId().equals(warnRecordEntity.getRuleid())).findFirst().orElse(null);

                if (ObjectUtil.isNotEmpty(rule)){
                    warns.add(setWarnRecordVO(warnRecordEntity , rule.getType() ));
                }

            }
        }
        return warns;

    }

    private WarnRecordVO setWarnRecordVO(WarnRecordEntity warnRecordEntity , int type){

        WarnRecordVO warnRecordVO = new WarnRecordVO();
        warnRecordVO.setId(warnRecordEntity.getId());

        if (ObjectUtil.isNotEmpty(warnRecordEntity.getArea())) {
            AreaEntity areaEntity = areaDao.selectByUid(warnRecordEntity.getArea().intValue());
            if (ObjectUtil.isNotEmpty(areaEntity)){
                warnRecordVO.setArea(areaEntity.getAlias());
            }
        }

        warnRecordVO.setCount(warnRecordEntity.getCount());
        warnRecordVO.setDelay(warnRecordEntity.getDelay());
        warnRecordVO.setDevid(warnRecordEntity.getDevid());
        warnRecordVO.setName(warnRecordEntity.getName());
        warnRecordVO.setAlias(warnRecordEntity.getAlias());
        warnRecordVO.setLevel(warnRecordEntity.getLevel());
        warnRecordVO.setRuleid(warnRecordEntity.getRuleid());
        warnRecordVO.setCreateTime(warnRecordEntity.getCreateTime());
        warnRecordVO.setCloseTime(warnRecordEntity.getCloseTime());
        warnRecordVO.setStatus(warnRecordEntity.getStatus());
        warnRecordVO.setWarnid(warnRecordEntity.getWarnid());

        if (ObjectUtil.isNotEmpty(warnRecordEntity.getSubsys())){
            SubsysEntity subsysEntity = subsysDao.selectByUid(warnRecordEntity.getSubsys().intValue());

            if (ObjectUtil.isNotEmpty(subsysEntity)){
                warnRecordVO.setSubsys(subsysEntity.getAlias());
            }
        }

        Device device =  cacheService.get(warnRecordEntity.getDevid());
        warnRecordVO.setDevName(device.getAlias());
        warnRecordVO.setType(type);

        return warnRecordVO;

    }


    @ApiOperation(value = "手工关闭告警")
    @PostMapping( "closeWarn")
    public boolean closeWarn(@RequestParam int id ) {

       WarnRecordEntity warnRecordEntity = warnRecordDao.selectByUid(BigInteger.valueOf(id));

       if (ObjectUtil.isEmpty(warnRecordEntity) || warnRecordEntity.getStatus() != 2 ){
           return false;
       }

       warnRecordEntity.setStatus(0);
       warnRecordEntity.setCloseTime(DateUtil.date());
        warnRecordDao.update(warnRecordEntity);

        return true;
    }


    @ApiOperation(value = "获取告警统计")
    @GetMapping( "getWarnTotal")
    public WarnTotal getWarnTotal() {

        LocalDateTime now = LocalDateTime.now();

        // 定义格式为yyyyMMdd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        String yearMonth = now.format(formatter);

       List<WarnRecordEntity> res = warnRecordDao.selectByMonth(yearMonth);

       WarnTotal warnTotal = new WarnTotal();

       if (ObjectUtil.isEmpty(res)){
           warnTotal.setWarnClose("0");
           warnTotal.setWarnTicket("0");
           warnTotal.setWarnTotal("0");
           warnTotal.setWarnDeviceToday("0");
           warnTotal.setWarnHealthToday("0");

           return warnTotal;
       }

       int total = (int) res.stream().filter(v -> v.getStatus() == 1).count();
       int closeWarn = (int) res.stream().filter(v -> v.getStatus() == 0).count();
       int ticketWarn = (int) res.stream().filter(v -> v.getStatus() == 4).count();

       warnTotal.setWarnTotal(String.valueOf(total));
       warnTotal.setWarnClose(String.valueOf(closeWarn));
       warnTotal.setWarnTicket(String.valueOf(ticketWarn));


        List<WarnRecordEntity> today = warnRecordDao.selectByDate(LocalDate.now());

        if (ObjectUtil.isEmpty(today)){
            warnTotal.setWarnDeviceToday("0");
            warnTotal.setWarnHealthToday("0");
        }else {
            int warnHealth = (int) today.stream().filter(v -> v.getSubsys().intValue() == 2).count();
            int warnDevice = today.size() - warnHealth;

            warnTotal.setWarnDeviceToday(String.valueOf(warnDevice));
            warnTotal.setWarnHealthToday(String.valueOf(warnHealth));
        }


        return warnTotal;

    }

    @ApiOperation(value = "获取节能统计")
    @GetMapping( "getEnergySavingTotal")
    public EnergySavingTotal getEnergySavingTotal() {

        LocalDateTime now = LocalDateTime.now();

        // 定义格式为yyyyMMdd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        String yearMonth = now.format(formatter);

        List<WarnRecordEntity> warnRecordEntities = warnRecordDao.selectByMonth(yearMonth);
        List<WarnRuleEntity> rules = warnRuleDao.selectAll();


        EnergySavingTotal energySavingTotal = new EnergySavingTotal();

        if (ObjectUtil.isEmpty(warnRecordEntities)){
            energySavingTotal.setWarnClose("0");
            energySavingTotal.setWarnTicket("0");
            energySavingTotal.setWarnTotal("0");

            return energySavingTotal;
        }


       List<WarnRecordEntity> res = new ArrayList<>();

        for (WarnRecordEntity warnRecordEntity : warnRecordEntities){

            if (ObjectUtil.isNotEmpty(warnRecordEntity)  ){
                WarnRuleEntity rule = rules.stream().filter(v -> v.getId().equals(warnRecordEntity.getRuleid())).findFirst().orElse(null);

                if (ObjectUtil.isNotEmpty(rule) && rule.getType() == 2){
                    res.add(warnRecordEntity);
                }
            }
        }


        int total = res.size();
        int closeWarn = (int) res.stream().filter(v -> v.getStatus() == 0).count();
        int ticketWarn = (int) res.stream().filter(v -> v.getStatus() == 4).count();

        energySavingTotal.setWarnTotal(String.valueOf(total));
        energySavingTotal.setWarnClose(String.valueOf(closeWarn));
        energySavingTotal.setWarnTicket(String.valueOf(ticketWarn));



        return energySavingTotal;

    }


}
