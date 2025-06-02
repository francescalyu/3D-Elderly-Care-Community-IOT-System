package com.francesca.controller;


import com.francesca.constant.UrlConstant;
import com.francesca.model.VO.dash.DashAirVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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


    @ApiOperation(value = "获取电量")
    @GetMapping( "getPower")
    public DashAirVO getPower() {
        System.out.println(UrlConstant.Dashboard_Power + "/getPower");

        return new DashAirVO();
    }



}
