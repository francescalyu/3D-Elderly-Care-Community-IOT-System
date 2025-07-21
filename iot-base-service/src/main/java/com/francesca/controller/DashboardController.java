package com.francesca.controller;


import cn.hutool.core.util.ObjectUtil;
import com.francesca.constant.UrlConstant;
import com.francesca.dao.Health1hDao;
import com.francesca.dao.Power5minDao;
import com.francesca.model.DTO.Health1hEntity;
import com.francesca.model.DTO.Power5minEntity;
import com.francesca.model.VO.dash.*;
import com.francesca.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author francesca

 * 2025-05-16
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Dashboard)
@Validated
@Api(tags = "看板")
public class DashboardController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Power5minDao power5minDao;

    @Autowired
    private Health1hDao health1hDao;

    @ApiOperation(value = "获取当前空气质量")
    @GetMapping( "getAir")
    public DashAirVO getAir() {
        return cacheService.getCurrentAir();
    }

    @ApiOperation(value = "获取当日能源")
    @GetMapping( "getPower")
    public DashPowerVO getPower() {
        return cacheService.getCurrentPower();
    }

    @ApiOperation(value = "获取1小时能源 , 日期YYYYMMDD")
    @GetMapping( "getPowerbyHour")
    public List<Power5minEntity> getPowerbyHour(String qdate) {

        if(ObjectUtil.isEmpty(qdate) || qdate.length() < 8 ){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(qdate, formatter);

        return power5minDao.selectbydate(localDate,2);

    }

    @ApiOperation(value = "获取指定月份1日能源 , 日期YYYYMM")
    @GetMapping( "getPowerbyDay")
    public List<Power5minEntity> getPowerbyDay(String qdate) {

        if(ObjectUtil.isEmpty(qdate) || qdate.length() < 6 ){
            return null;
        }


        return power5minDao.selectByMonth(qdate, 3);

    }

    @ApiOperation(value = "获取1小时健康数据 , 日期YYYYMMDD")
    @GetMapping( "getHealthbyHour")
    public List<Health1hEntity> getHealthbyHour(String qdate) {

        if(ObjectUtil.isEmpty(qdate) || qdate.length() < 8 ){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(qdate, formatter);

        return health1hDao.selectbydate(localDate);

    }

    @ApiOperation(value = "获取健康数据")
    @GetMapping( "getHealth")
    public HealthVO getHealth() {
        return cacheService.getHealth();
    }

    @ApiOperation(value = "获取门禁及户外数据")
    @GetMapping( "getOutDoor")
    public DashDoorVO getOutDoor() {
        return cacheService.getDashDoor();
    }




}
