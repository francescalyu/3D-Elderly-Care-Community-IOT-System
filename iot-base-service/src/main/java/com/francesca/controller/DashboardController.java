package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.model.VO.dash.DashAirVO;
import com.francesca.model.VO.dash.DashDoorVO;
import com.francesca.model.VO.dash.DashPowerVO;
import com.francesca.model.VO.dash.HealthVO;
import com.francesca.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author francesca

 * 2025-05-16
 */

@Slf4j
@RestController
@RequestMapping(UrlConstant.Dashboard)
@Validated
@Api(tags = "Dashboard")
public class DashboardController {

    @Autowired
    private CacheService cacheService;

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
